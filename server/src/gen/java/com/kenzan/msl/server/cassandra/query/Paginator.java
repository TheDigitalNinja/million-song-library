/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Select.Builder;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.kenzan.msl.server.bo.AbstractBo;
import com.kenzan.msl.server.bo.AbstractListBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.dao.AbstractDao;
import com.kenzan.msl.server.dao.FacetDao;
import com.kenzan.msl.server.manager.FacetManager;

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
		this.items = (items >= CassandraConstants.MSL_BROWSE_MIN_PAGE_SIZE && items <= CassandraConstants.MSL_BROWSE_MAX_PAGE_SIZE) ? items : CassandraConstants.MSL_BROWSE_DEFAULT_PAGE_SIZE;
		
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
		final String pagingStateUuid = UUID.randomUUID().toString();

		// Build the SELECT query
		Select select = buildSelectQuery();
		
		// Execute the query
		ResultSet resultSet = session.execute(select);
		
		// Populate the AbstractListBo with the results of the query 
		buildAbstractListBo(resultSet, pagingStateUuid, abstractListBo);
		
		// TODO Insert PagingState instance into Cassandra [use resultSet.getExecutionInfo().getPagingState()], for retrieval on subsequent page requests
		// TODO Queue background thread to retrieve next page
		
		return;
	}
	
	/*
	 * Retrieves a subsequent page (that is: not the first page) of content and populates the AbstractListBo accordingly.
	 * 
	 * @param abstractListBo the AbstractListBo that will be populated with the page's data.
	 */
	private void getSubsequentPage(AbstractListBo<? extends AbstractBo> abstractListBo) {
		// Use SimpleStatement to inject String query and use Cassandra pagingState from previous page
		
		return;
	}
	
	/*
	 * Build the SELECT query to retrieve data from the appropriate table (based on any facet(s) passed from the caller)
	 * and limiting the number of results to the page size requested by the caller.
	 * 
	 * @returns a Select query based on the facet(s), if any, and the page size requested by the caller.
	 */
	private Select buildSelectQuery() {
		Builder builder = QueryBuilder.select().all();
		
		Select select;
		if (hasFacets()) {
			// Add the FROM clause
			select = builder.from(contentType.facetTableName);
			
			// Add the WHERE clause
			select.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_FACET_NAME, facets.get(0).getFacetName()))
					.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, contentType.dbContentType));
		}
		else {
			// Add the FROM clause
			select = builder.from(contentType.featuredTableName);
			
			// Add the WHERE clause
			select.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_HOTNESS_BUCKET, CassandraConstants.MSL_DEFAULT_HOTNESS_BUCKET))
					.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, contentType.dbContentType));
		}
		select.setFetchSize(items);

		return select;
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
	private void buildAbstractListBo(ResultSet resultSet, final String pagingStateUuid, AbstractListBo<? extends AbstractBo> abstractListBo) {
		// Map the results from the resultSet to our BO POJO
		Class<? extends AbstractDao> boClass = hasFacets() ? contentType.facetContentDaoClass : contentType.featuredContentDaoClass;
		Result<? extends AbstractDao> mappedResults = mappingManager.mapper(boClass).map(resultSet);
		for (AbstractDao dao : mappedResults) {
			abstractListBo.addDao(dao);
			
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
	 * Helper method to see if any facets exist 
	 * 
	 * @return true if facets exist, false otherwise
	 */
	private boolean hasFacets() {
		return !(null == facets || facets.size() == 0);
	}

}
