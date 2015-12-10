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
@Table(name = "artists_by_facet")
public class ArtistsByFacetDao extends AbstractArtistDao {
    @PartitionKey(value = 0)
    @Column(name = "facet_name")
    private String facetName;
    @PartitionKey(value = 1)
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "artist_id")
    private UUID artistId;
    @Column(name = "artist_name")
    private String artistName;
    @Column(name = "artist_mbid")
    private UUID artistMbid;

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

    /**
     * @return the artistId
     */
    @Override
    public UUID getArtistId() {
        return artistId;
    }

    /**
     * @param artistId the artistId to set
     */
    @Override
    public void setArtistId(UUID artistId) {
        this.artistId = artistId;
    }

    /**
     * @return the artistName
     */
    @Override
    public String getArtistName() {
        return artistName;
    }

    /**
     * @param artistName the artistName to set
     */
    @Override
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * @return the artistMbid
     */
    @Override
    public UUID getArtistMbid() {
        return artistMbid;
    }

    /**
     * @param artistMbid the artistMbid to set
     */
    @Override
    public void setArtistMbid(UUID artistMbid) {
        this.artistMbid = artistMbid;
    }

}
