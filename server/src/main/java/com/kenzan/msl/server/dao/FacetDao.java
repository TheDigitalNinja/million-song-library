/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.dao;

import com.kenzan.msl.server.bo.AbstractBo;

/**
 * 
 * 
 * @author billschwanitz
 */
public class FacetDao extends AbstractBo {
    private String facetId;
    private String facetName;

    public FacetDao() {
    }

    /**
     * @param facetId facetId to set
     * @param facetName facetName to set
     */
    public FacetDao( String facetId, String facetName ) {
        setFacetId(facetId);
        setFacetName(facetName);
    }

    /**
     * @return the facetId
     */
    public String getFacetId() {
        return facetId;
    }

    /**
     * @param facetId the facetId to set
     */
    public void setFacetId(String facetId) {
        this.facetId = facetId;
    }

    /**
     * @return the facetName
     */
    public String getFacetName() {
        return facetName;
    }

    /**
     * @param facetName the facetName to set
     */
    public void setFacetName(String facetName) {
        this.facetName = facetName;
    }
}
