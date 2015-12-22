/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.dao;

import java.util.UUID;

/**
 * @author billschwanitz
 */
public abstract class AbstractAlbumDao extends AbstractDao {

    /**
     * @return the albumId
     */
    public abstract UUID getAlbumId();

    /**
     * @param albumId the albumId to set
     */
    public abstract void setAlbumId(UUID albumId);

    /**
     * @return the albumName
     */
    public abstract String getAlbumName();

    /**
     * @param albumName the albumName to set
     */
    public abstract void setAlbumName(String albumName);

    /**
     * @return the albumYear
     */
    public abstract int getAlbumYear();

    /**
     * @param albumYear the albumYear to set
     */
    public abstract void setAlbumYear(int albumYear);

    /**
     * @return the artistId
     */
    public abstract UUID getArtistId();

    /**
     * @param artistId the artistId to set
     */
    public abstract void setArtistId(UUID artistId);

    /**
     * @return the artistName
     */
    public abstract String getArtistName();

    /**
     * @param artistName the artistName to set
     */
    public abstract void setArtistName(String artistName);

    /**
     * @return the artistMbid
     */
    public abstract UUID getArtistMbid();

    /**
     * @param artistMbid the artistMbid to set
     */
    public abstract void setArtistMbid(UUID artistMbid);

    /**
     * @return the album imageLink
     */
    public abstract String getImageLink();

    /**
     * @param imageLink the imageLink to set
     */
    public abstract void setImageLink(String imageLink);

}
