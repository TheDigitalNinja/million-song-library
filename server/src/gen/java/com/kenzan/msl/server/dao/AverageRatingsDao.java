/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.dao;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

/**
 *
 *
 * @author billschwanitz
 */
@Table(name = "average_ratings")
public class AverageRatingsDao extends AbstractDao {
	@PartitionKey
	@Column (name = "content_id")
	private UUID contentId;
	@Column (name = "content_type")
	private String contentType;
	@Column (name = "num_rating")
	private int numRating;
	@Column (name = "sum_rating")
	private int sumRating;
	
	/**
	 * @return the contentId
	 */
	public UUID getContentId() {
		return contentId;
	}
	/**
	 * @param contentId the contentId to set
	 */
	public void setContentId(UUID contentId) {
		this.contentId = contentId;
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
	/**
	 * @return the numRating
	 */
	public int getNumRating() {
		return numRating;
	}
	/**
	 * @param numRating the numRating to set
	 */
	public void setNumRating(int numRating) {
		this.numRating = numRating;
	}
	/**
	 * @return the sumRating
	 */
	public int getSumRating() {
		return sumRating;
	}
	/**
	 * @param sumRating the sumRating to set
	 */
	public void setSumRating(int sumRating) {
		this.sumRating = sumRating;
	}
}
