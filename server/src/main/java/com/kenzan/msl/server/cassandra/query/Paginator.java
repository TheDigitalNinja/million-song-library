/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Mapper.Option;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.server.bo.AbstractBo;
import com.kenzan.msl.server.bo.AbstractListBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.QueryAccessor;
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
    private final QueryAccessor queryAccessor;
    private final MappingManager mappingManager;
    private final PaginatorHelper paginatorHelper;
    private final UUID pagingStateUuid;
    private final Integer items;
    private final List<FacetDao> facets;

    // TODO put PAGING_STATE_TTL in config param
    // Number of seconds before a Paging State expires from the DB. Setting to 0 allows Paging
    // States to exist forever, or until explicitly deleted.
    private static final int PAGING_STATE_TTL_SECS = 60 * 60; // 1 hour;

    /*
     * Constructor
     * 
     * @param contentType the type of content to be retrieved
     * 
     * @param queryAccessor the Datastax QueryAccessor declaring our prepared queries
     * 
     * @param mappingManager the Datastax MappingManager responsible for executing queries and
     * mapping results to POJOs
     * 
     * @param paginatorHelper a PaginatorHelper instance to make queries to the appropriate tables
     * 
     * @param pagingStateuid a UUID identifier of the current paging location. Will be null for the
     * first page and non-null for subsequent pages)
     * 
     * @param items the number of items to be included in each page
     * 
     * @param facets a comma delimited list of zero or more facet Ids to use to filter the results
     */
    public Paginator( final CassandraConstants.MSL_CONTENT_TYPE contentType, final QueryAccessor queryAccessor,
                      final MappingManager mappingManager, final PaginatorHelper paginatorHelper,
                      final UUID pagingStateUuid, final Integer items, final String facets ) {
        this.contentType = contentType;
        this.queryAccessor = queryAccessor;
        this.mappingManager = mappingManager;
        this.paginatorHelper = paginatorHelper;
        this.pagingStateUuid = pagingStateUuid;

        // Enforce allowable range of items. If outside the range use a default.
        this.items = (null == items || items < CassandraConstants.MSL_BROWSE_MIN_PAGE_SIZE || items > CassandraConstants.MSL_BROWSE_MAX_PAGE_SIZE)
                                                                                                                                                  ? CassandraConstants.MSL_BROWSE_DEFAULT_PAGE_SIZE
                                                                                                                                                  : items;

        // Parse the comma delimited list of facet Ids
        this.facets = new ArrayList<>();
        if ( !StringUtils.isEmpty(facets) ) {
            String[] facetIds = facets.split(",");

            for ( String facetId : facetIds ) {
                Optional<FacetDao> optFacetDao = FacetManager.getInstance().getFacet(facetId);

                if ( !optFacetDao.isPresent() ) {
                    // TODO Do something here
                }

                this.facets.add(optFacetDao.get());
            }
        }
    }

    /*
     * Retrieves a page of content and populates the AbstractListBo accordingly.
     * 
     * @param contentListBo the AbstractListBo that will be populated with the page's data.
     */
    public void getPage(AbstractListBo<? extends AbstractBo> abstracttListBo) {
        if ( null == pagingStateUuid ) {
            getFirstPage(abstracttListBo);
        }
        else {
            getSubsequentPage(abstracttListBo);
        }

        return;
    }

    /*
     * +-----------------+ | Private Methods | +-----------------+
     */

    /*
     * Retrieves the first page of content and populates the AbstractListBo accordingly.
     * 
     * @param abstractListBo the AbstractListBo that will be populated with the page's data.
     */
    private void getFirstPage(AbstractListBo<? extends AbstractBo> abstractListBo) {
        // Generate the pagingState to be used for retrieval of subsequent pages (if any)
        final UUID pagingStateUuid = UUID.randomUUID();

        // Prepare the query and retrieve the query string based on whether this is a faceted search
        // or just featured ordering
        Statement statement;
        String queryString;
        if ( hasFacets() ) {
            statement = paginatorHelper.prepareFacetedQuery(queryAccessor, this.facets.get(0).getFacetName());
            queryString = paginatorHelper.getFacetedQueryString(this.facets.get(0).getFacetName());
        }
        else {
            statement = paginatorHelper.prepareFeaturedQuery(queryAccessor);
            queryString = paginatorHelper.getFeaturedQueryString();
        }

        // Set the fetch size to the size of a page
        statement.setFetchSize(items);

        // Execute the query
        ResultSet resultSet = mappingManager.getSession().execute(statement);

        // Populate the AbstractListBo with the results of the query
        buildAbstractListBo(resultSet, pagingStateUuid, abstractListBo);

        // If there is a subsequent page, then add row to paging_state table
        if ( abstractListBo.getPagingState() != null ) {
            addPagingState(pagingStateUuid, queryString, resultSet);
        }

        // TODO Queue background thread to retrieve next page

        return;
    }

    /*
     * Retrieves a subsequent page (that is: not the first page) of content and populates the
     * AbstractListBo accordingly.
     * 
     * @param abstractListBo the AbstractListBo that will be populated with the page's data.
     * 
     * @return true on success, false on failure
     */
    private boolean getSubsequentPage(AbstractListBo<? extends AbstractBo> abstractListBo) {
        // Attempt to retrieve the paging state DAO by primary key
        Optional<PagingStateDao> optPagingStateDao = retrievePagingState(pagingStateUuid);

        // If the paging state DAO does not exist
        if ( !optPagingStateDao.isPresent() ) {
            return false;
        }

        PagingStateDao pagingStateDao = optPagingStateDao.get();

        Statement statement = new SimpleStatement(pagingStateDao.getPagingState().getQuery())
            .setPagingStateUnsafe(pagingStateDao.getPagingState().getPageStateBlob())
            .setFetchSize(pagingStateDao.getPagingState().getPageSize());

        // Execute the query
        ResultSet resultSet = mappingManager.getSession().execute(statement);

        // Populate the AbstractListBo with the results of the query
        buildAbstractListBo(resultSet, pagingStateUuid, abstractListBo);

        // If there is a subsequent page, then update row in paging_state table, otherwise delete
        // the row
        if ( null == abstractListBo.getPagingState() ) {
            deletePagingState(pagingStateUuid);
        }
        else {
            savePagingState(pagingStateDao, resultSet);
        }

        // TODO Queue background thread to retrieve next page

        return true;
    }

    /*
     * Populates the AbstractListBo with the results from <code>resultSet</code> and, optionally,
     * the <code>pagingStateUuid</code>. If the results are the last results in the table, then
     * DON'T include the <code>pagingStateUuid</code> in the AbstractListBo. This is the flag to the
     * caller that the last page of data has been retrieved.
     * 
     * @param resultSet the query results that should be used to build the page
     * 
     * @param pagingStateUuid the pagingState to include in the AbstractListBo if this is NOT the
     * last page of data.
     * 
     * @param abstractListBo the AbstractListBo that will be populated with the page's data.
     */
    // TODO Resolve the issue with adding elements to a generic List<? extends AbstractBo>. This is
    // the reason for the SuppressWarnings annotation.
    private void buildAbstractListBo(ResultSet resultSet, final UUID pagingStateUuid,
                                     AbstractListBo<? extends AbstractBo> abstractListBo) {
        // Map the results from the resultSet to our BO POJO
        Class<? extends AbstractDao> boClass = hasFacets() ? contentType.facetContentDaoClass
                                                          : contentType.featuredContentDaoClass;
        Result<? extends AbstractDao> mappedResults = mappingManager.mapper(boClass).map(resultSet);
        for ( AbstractDao dao : mappedResults ) {
            abstractListBo.add(dao);

            /*
             * Have we reached the end of the page (set via the fetch size on the Statement)? If
             * this check were not performed, the Datastax driver would silently get the next page
             * of results. This is a cool feature in some use cases, we don't want the Datastax
             * driver to do it in this case.
             */
            if ( resultSet.getAvailableWithoutFetching() == 0 ) {
                break;
            }
        }

        // Have we reached the end of the table?
        if ( !resultSet.isFullyFetched() ) {
            // If not, then include the paging state UUID in the response so the caller knows there
            // is a subsequent page.
            abstractListBo.setPagingState(pagingStateUuid);
        }

        return;
    }

    /*
     * Add a row to the Cassandra paging_state table with the info appropriate to query for the
     * subsequent page
     * 
     * @param pagingStateUuid the UUID to assign to this paging state row. This is the UUID that
     * will be sent to the client and expected in requests for subsequent pages.
     * 
     * @param query the actual query string. This is required to use the Cassandra PageState.
     * 
     * @param resultSet the resultSet that has just been consumed
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
     * Add/update a row in the Cassandra paging_state table. This method is used for both adding new
     * rows and updating existing rows.
     * 
     * @param pagingStateDao the DAO that will receive updated Cassandra page state info then be
     * written to the DB
     * 
     * @param resutlSet the current result set from which the Cassandra page state info will be
     * extracted
     */
    private void savePagingState(PagingStateDao pagingStateDao, final ResultSet resultSet) {
        // Put the Cassandra PageState into the PagingStateUdt
        byte[] cassandraPageState = resultSet.getExecutionInfo().getPagingStateUnsafe();
        if ( null == cassandraPageState ) {
            pagingStateDao.getPagingState().setPageState(null);
        }
        else {
            ByteBuffer byteBuffer = ByteBuffer.allocate(cassandraPageState.length);
            byteBuffer.put(cassandraPageState);
            byteBuffer.flip(); // Have to do this to reset the internals of the ByteBuff to prepare
            // it to be consumed
            pagingStateDao.getPagingState().setPageState(byteBuffer);
        }

        // Get a mapper and do the add/update
        mappingManager.mapper(PagingStateDao.class).save(pagingStateDao, Option.ttl(PAGING_STATE_TTL_SECS));
    }

    /*
     * Retrieve a paging state DAO using the paging state UUID received from the client. Will retry
     * multiple times if the background thread has not yet populated the buffer.
     * 
     * @param pagingStateUuid the paging state UUID sent to the client as a response to the query
     * for the previous page
     * 
     * @returns Optional paging state DAO retrieved from Cassandra, or absent() if not found.
     */
    private Optional<PagingStateDao> retrievePagingState(UUID pagingStateUuid) {
        // Get a mapper to do the read
        Mapper<PagingStateDao> mapper = mappingManager.mapper(PagingStateDao.class);

        // Do the read
        PagingStateDao pagingStateDao = mapper.get(pagingStateUuid);

        // If the paging state DAO does not exist then return
        if ( null == pagingStateDao ) {
            return Optional.absent();
        }

        return Optional.of(pagingStateDao);
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
