/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.dao;

import java.util.List;

/**
 *
 *
 * @author billschwanitz
 */
public class FacetWithChildrenDao extends FacetDao {
    private List<FacetDao> children;

    /**
     * @return the children
     */
    public List<FacetDao> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<FacetDao> children) {
        this.children = children;
    }
}
