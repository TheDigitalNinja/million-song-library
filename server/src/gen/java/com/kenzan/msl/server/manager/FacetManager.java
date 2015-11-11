/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.manager;

import com.kenzan.msl.server.dao.FacetDao;
import com.kenzan.msl.server.dao.FacetWithChildrenDao;

/**
 *
 *
 * @author billschwanitz
 */
public class FacetManager {
	private static FacetManager instance = new FacetManager();
	
	private FacetManager() {}
	
	public static FacetManager getInstance() {
		return instance;
	}
	
	public FacetDao getFacet(String id) {
		// TODO return something
		return null;
	}

	public FacetWithChildrenDao getFacetWithChildren(String id) {
		// TODO return something
		return null;
	}
}
