/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.Statement;
import com.kenzan.msl.server.cassandra.QueryAccessor;

/**
 * 
 * 
 * @author billschwanitz
 */
public interface PaginatorHelper {
    public Statement prepareFacetedQuery(final QueryAccessor queryAccessor, final String facetName);

    public Statement prepareFeaturedQuery(final QueryAccessor queryAccessor);

    public String getFacetedQueryString(final String facetName);

    public String getFeaturedQueryString();
}
