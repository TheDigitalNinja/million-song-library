/*
 * Copyright 2015, Kenzan, All rights reserved.
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
    @Column(name = "content_id")
    private UUID contentId;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "num_rating")
    private Long numRating;
    @Column(name = "sum_rating")
    private Long sumRating;

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
    public Long getNumRating() {
        return numRating;
    }

    /**
     * @param numRating the numRating to set
     */
    public void setNumRating(Long numRating) {
        this.numRating = numRating;
    }

    /**
     * @return the sumRating
     */
    public Long getSumRating() {
        return sumRating;
    }

    /**
     * @param sumRating the sumRating to set
     */
    public void setSumRating(Long sumRating) {
        this.sumRating = sumRating;
    }
}
