/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.dao;

import java.util.UUID;

/**
 * 
 * 
 * @author billschwanitz
 */
public abstract class AbstractSongDao extends AbstractDao {

    /**
     * @return the songId
     */
    public abstract UUID getSongId();

    /**
     * @param songId the songId to set
     */
    public abstract void setSongId(UUID songId);

    /**
     * @return the songName
     */
    public abstract String getSongName();

    /**
     * @param songName the songName to set
     */
    public abstract void setSongName(String songName);

    /**
     * @return the songDuration
     */
    public abstract Integer getSongDuration();

    /**
     * @param songDuration the songDuration to set
     */
    public abstract void setSongDuration(Integer songDuration);

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
    public abstract Integer getAlbumYear();

    /**
     * @param albumYear the albumYear to set
     */
    public abstract void setAlbumYear(Integer albumYear);

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

}
