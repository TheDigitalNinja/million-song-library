/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.dao;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;
import java.util.UUID;

/**
 *
 *
 * @author billschwanitz
 */
@Table(name = "user_data_by_user")
public class UserDataByUserDao extends AbstractDao {
    @PartitionKey
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "content_uuid")
    private UUID contentUuid;
    @Column(name = "favorites_timestamp")
    private Date favoritesTimestamp;
    @Column(name = "rating")
    private Integer rating;

    /**
     * @return the userId
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
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
     * @return the contentUuid
     */
    public UUID getContentUuid() {
        return contentUuid;
    }

    /**
     * @param contentUuid the contentUuid to set
     */
    public void setContentUuid(UUID contentUuid) {
        this.contentUuid = contentUuid;
    }

    /**
     * @return the favoritesTimestamp
     */
    public Date getFavoritesTimestamp() {
        return favoritesTimestamp;
    }

    /**
     * @param favoritesTimestamp the favoritesTimestamp to set
     */
    public void setFavoritesTimestamp(Date favoritesTimestamp) {
        this.favoritesTimestamp = favoritesTimestamp;
    }

    /**
     * @return the rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
