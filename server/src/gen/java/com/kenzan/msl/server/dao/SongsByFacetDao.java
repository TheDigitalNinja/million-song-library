/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.dao;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 *
 *
 * @author billschwanitz
 */
@Table(name = "songs_by_facet")
public class SongsByFacetDao extends AbstractSongDao {
	@PartitionKey(value = 0)
	@Column (name = "facet_name")
	private String facetName;
	@PartitionKey(value = 1)
	@Column (name = "content_type")
	private String contentType;
	
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
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
