/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.kenzan.msl.server.bo.AbstractBo;
import com.kenzan.msl.server.bo.AbstractListBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.dao.AbstractDao;
import com.kenzan.msl.server.dao.FacetDao;
import com.kenzan.msl.server.dao.PagingStateDao;
import com.kenzan.msl.server.manager.FacetManager;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * Handle paginated browse queries of any content (Album, Artist, Song).
 *
 * @author billschwanitz
 */
public class Paginator {
	private final CassandraConstants.MSL_CONTENT_TYPE contentType;
	private final Session session;
	private final UUID pagingStateUuid;
	private final Integer items;
	private final List<FacetDao> facets;
	
	private final MappingManager mappingManager;

	// TODO put MAX_RETRIES in config param
	//private static final int MAX_RETRIES = 10;
	// TODO put SLEEP_DURATION in config param
	//private static final int SLEEP_DURATION = 500;

	/*
	 * Constructor
	 * 
	 * @param contentType		the type of content to be retrieved
	 * @param session			the Datastax session to use to perform queries
	 * @param pagingStateuid	a UUID identifier of the current paging location. Will be null for the first page and non-null for subsequent pages)
	 * @param items				the number of items to be included in each page
	 * @param facets			a comma delimited list of zero or more facet Ids to use to filter the results
	 */
	public Paginator(final CassandraConstants.MSL_CONTENT_TYPE contentType, final Session session, final UUID pagingStateUuid, final Integer items, final String facets) {
		this.contentType = contentType;
		this.session = session;
		this.pagingStateUuid = pagingStateUuid;

		// Enforce allowable range of items. If outside the range use a default.
		this.items = (null == items || items < CassandraConstants.MSL_BROWSE_MIN_PAGE_SIZE || items > CassandraConstants.MSL_BROWSE_MAX_PAGE_SIZE) ? CassandraConstants.MSL_BROWSE_DEFAULT_PAGE_SIZE : items;
		
		// Parse the comma delimited list of facet Ids
		this.facets = new ArrayList<FacetDao>();
		if (!StringUtils.isEmpty(facets)) {
			String[] facetIds = facets.split(",");

			for (String facetId : facetIds) {
				FacetDao facetDao = FacetManager.getInstance().getFacet(facetId);
				
				if (null == facetDao) {
					// TODO Do something here
				}
				
				this.facets.add(facetDao);
			}
		}
		
		// Construct a MappingManager from the session to use later for converting DB rows to DAO POJOs
		mappingManager = new MappingManager(session);
	}
	
	/*
	 * Retrieves a page of content and populates the AbstractListBo accordingly.
	 * 
	 * @param contentListBo the AbstractListBo that will be populated with the page's data.
	 */
	public void getPage(AbstractListBo<? extends AbstractBo> abstracttListBo) {
		if (null == pagingStateUuid) {
			getFirstPage(abstracttListBo);
		}
		else {
			getSubsequentPage(abstracttListBo);
		}
		
		return;
	}
	
	/*
	 * +-----------------+
	 * | Private Methods |
	 * +-----------------+
	 */

	/*
	 * Retrieves the first page of content and populates the AbstractListBo accordingly.
	 * 
	 * @param abstractListBo the AbstractListBo that will be populated with the page's data.
	 */
	private void getFirstPage(AbstractListBo<? extends AbstractBo> abstractListBo) {
		// Generate the pagingState to be used for retrieval of subsequent pages (if any)
		final UUID pagingStateUuid = UUID.randomUUID();

		// Build the SELECT query
		RegularStatement statement = buildSelectQuery();
		
		// Execute the query
		ResultSet resultSet = session.execute(statement);
		
		// Populate the AbstractListBo with the results of the query 
		buildAbstractListBo(resultSet, pagingStateUuid, abstractListBo);
		
		// If there is a subsequent page, then add row to paging_state table
		if (abstractListBo.getPagingState() != null) {
			addPagingState(pagingStateUuid, statement.getQueryString(), resultSet);
		}
		
		// TODO Queue background thread to retrieve next page
		
		return;
	}
	
	/*
	 * Retrieves a subsequent page (that is: not the first page) of content and populates the AbstractListBo accordingly.
	 * 
	 * @param abstractListBo the AbstractListBo that will be populated with the page's data.
	 * 
	 * @return true on success, false on failure
	 */
	private boolean getSubsequentPage(AbstractListBo<? extends AbstractBo> abstractListBo) {
		// Attempt to retrieve the paging state DAO by primary key
		PagingStateDao pagingStateDao = retrievePagingState(pagingStateUuid);

		// If the paging state DAO does not exist
		if (null == pagingStateDao) {
			return false;
		}

		Statement statement = new SimpleStatement(pagingStateDao.getPagingState().getQuery())
									.setPagingStateUnsafe(pagingStateDao.getPagingState().getPageStateBlob())
									.setFetchSize(pagingStateDao.getPagingState().getPageSize());

		// Execute the query
		ResultSet resultSet = session.execute(statement);
		
		// Populate the AbstractListBo with the results of the query 
		buildAbstractListBo(resultSet, pagingStateUuid, abstractListBo);
		
		// If there is a subsequent page, then update row in paging_state table, otherwise delete the row
		if (null == abstractListBo.getPagingState()) {
			deletePagingState(pagingStateUuid);
		} else {
			savePagingState(pagingStateDao, resultSet);
		}
		
		// TODO Queue background thread to retrieve next page
		
		return true;
	}
	
	/*
	 * Build the SELECT query to retrieve data from the appropriate table (based on any facet(s) passed from the caller)
	 * and limiting the number of results to the page size requested by the caller.
	 * 
	 * NOTE: It would be preferable to use QueryBuilder to build the query. Unfortunately, QueryBuilder uses bind variables
	 * 			for values in the WHERE clause and there does not appear to be simple way to retrieve the query string
	 * 			with bound values inserted for eventual use with the paging state.   
	 * 
	 * @returns a Select query based on the facet(s), if any, or the featured order, and the page size requested by the caller.
	 */
	private RegularStatement buildSelectQuery() {
		StringBuffer query = new StringBuffer();
		
		query.append("SELECT * FROM ");
		if (hasFacets()) {
			// Add the table name to the FROM clause
			query.append(contentType.facetTableName);
			
			// Add the WHERE clause
			query.append(" WHERE ");
			query.append(CassandraConstants.MSL_COLUMN_FACET_NAME);
			query.append("='");
			query.append(facets.get(0).getFacetName());
			query.append("' AND ");
			query.append(CassandraConstants.MSL_COLUMN_CONTENT_TYPE);
			query.append("='");
			query.append(contentType.dbContentType);
			query.append("'");
		}
		else {
			// Add the table name to the FROM clause
			query.append(contentType.featuredTableName);
			
			// Add the WHERE clause
			query.append(" WHERE ");
			query.append(CassandraConstants.MSL_COLUMN_HOTNESS_BUCKET);
			query.append("='");
			query.append(CassandraConstants.MSL_DEFAULT_HOTNESS_BUCKET);
			query.append("' AND ");
			query.append(CassandraConstants.MSL_COLUMN_CONTENT_TYPE);
			query.append("='");
			query.append(contentType.dbContentType);
			query.append("'");
		}
		
		SimpleStatement statement = new SimpleStatement(query.toString());
		statement.setFetchSize(items);
		
		return statement;
	}

	/*
	 * Populates the AbstractListBo with the results from <code>resultSet</code> and, optionally, the <code>pagingStateUuid</code>. If
	 * the results are the last results in the table, then DON'T include the <code>pagingStateUuid</code> in the AbstractListBo. This
	 * is the flag to the caller that the last page of data has been retrieved.
	 * 
	 * @param resultSet			the query results that should be used to build the page
	 * @param pagingStateUuid	the pagingState to include in the AbstractListBo if this is NOT the last page of data. 
	 * @param abstractListBo	the AbstractListBo that will be populated with the page's data.
	 */
	// TODO Resolve the issue with adding elements to a generic List<? extends AbstractBo>. This is the reason for the SuppressWarnings annotation.
	private void buildAbstractListBo(ResultSet resultSet, final UUID pagingStateUuid, AbstractListBo<? extends AbstractBo> abstractListBo) {
		// Map the results from the resultSet to our BO POJO
		Class<? extends AbstractDao> boClass = hasFacets() ? contentType.facetContentDaoClass : contentType.featuredContentDaoClass;
		Result<? extends AbstractDao> mappedResults = mappingManager.mapper(boClass).map(resultSet);
		for (AbstractDao dao : mappedResults) {
			abstractListBo.add(dao);
			
			if (resultSet.getAvailableWithoutFetching() == 0) {
				break;
			}
		}
		
		if (!resultSet.isFullyFetched()) {
			abstractListBo.setPagingState(pagingStateUuid);
		}
		
		return;
	}

	/*
	 * Add a row to the Cassandra paging_state table with the info appropriate to query for the subsequent page
	 * 
	 * @param pagingStateUuid	the UUID to assign to this paging state row. This is the UUID that will be sent
	 * 								to the client and expected in requests for subsequent pages.
	 * @param query				the actual query string. This is required to use the Cassandra PageState.
	 * @param resultSet			the resultSet that has just been consumed 
	 */
	private void addPagingState(final UUID pagingStateUuid, final String query, final ResultSet resultSet) {

		// Build the paging state user defined type (UDT)
		PagingStateDao.PagingStateUdt pagingStateUdt = new PagingStateDao.PagingStateUdt();
		pagingStateUdt.setPageSize(this.items);
		pagingStateUdt.setContentType(this.contentType.name());
		pagingStateUdt.setQuery(query);
		pagingStateUdt.setEnd(false);
		pagingStateUdt.setBuffer(null);
		
		// Build the paging state DAO
		PagingStateDao pagingStateDao = new PagingStateDao();
		pagingStateDao.setUserId(pagingStateUuid);
		pagingStateDao.setPagingState(pagingStateUdt);

		// Add the paging state DAO to Cassandra
		savePagingState(pagingStateDao, resultSet);
	}
	
	/*
	 * Add/update a row in the Cassandra paging_state table. This method is used for both
	 * adding new rows and updating existing rows.
	 * 
	 * @param pagingStateDao	the DAO that will receive updated Cassandra page state info then be written to the DB
	 * @param resutlSet			the current result set from which the Cassandra page state info will be extracted
	 */
	private void savePagingState(PagingStateDao pagingStateDao, final ResultSet resultSet) {
		// Put the Cassandra PageState into the PagingStateUdt
		byte[] cassandraPageState = resultSet.getExecutionInfo().getPagingStateUnsafe();
		if (null == cassandraPageState) {
			pagingStateDao.getPagingState().setPageState(null);
		} else {
			ByteBuffer byteBuffer = ByteBuffer.allocate(cassandraPageState.length);
			byteBuffer.put(cassandraPageState);
			byteBuffer.flip();	// Have to do this to reset the internals of the ByteBuff to prepare it to be consumed 
			pagingStateDao.getPagingState().setPageState(byteBuffer);
		}
		
		// Get a mapper and do the add/update
		mappingManager.mapper(PagingStateDao.class).save(pagingStateDao);
	}

	/*
	 * Retrieve a paging state DAO using the paging state UUID received from the client. Will retry multiple times
	 * if the background thread has not yet populated the buffer.
	 * 
	 * @param pagingStateUuid	the paging state UUID sent to the client as a response to the query for
	 * 								the previous page
	 * 
	 * @returns the paging state DAO retrieved from Cassandra and with a non-null buffer populated by the background thread,
	 * 				or null if not found or timed out waiting for background thread to populate the buffer
	 */
	private PagingStateDao retrievePagingState(UUID pagingStateUuid) {
		// Get a mapper to do the read
		Mapper<PagingStateDao> mapper = mappingManager.mapper(PagingStateDao.class);
		
		// Do the read
		PagingStateDao pagingStateDao = mapper.get(pagingStateUuid);

		// If the paging state DAO does not exist then return
		if (null == pagingStateDao) {
			return null;
		}
		
		/*
		 * Eventually, the background thread will populate the buffer, so, we'll wait for that to happen
		// Loop while the background thread has not populated the buffer and retry limit has not been exceeded
		for (int retries = 0; null == pagingStateDao.getPagingState().getBuffer() && retries < MAX_RETRIES; retries++) {
			// Wait a bit for the background thread to populate the buffer
			try {
				Thread.sleep(SLEEP_DURATION);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			
			// Re-retrieve the paging state DAO
			pagingStateDao = mapper.get(pagingStateUuid);
			// Handle the edge case where the paging state DAO existed initially but is now gone (TTL will cause this)
			if (null == pagingStateDao) {
				return null;
			}
		}
		
		if (null == pagingStateDao.getPagingState().getBuffer()) {
			return null;
		}
		*/
		
		return pagingStateDao;
	}
	
	private void deletePagingState(UUID pagingStateUuid) {
		// Use a mapper to do the delete
		mappingManager.mapper(PagingStateDao.class).delete(pagingStateUuid);
	}
	
	/*
	 * Helper method to see if any facets exist 
	 * 
	 * @return true if facets exist, false otherwise
	 */
	private boolean hasFacets() {
		return !(null == facets || facets.size() == 0);
	}

}
