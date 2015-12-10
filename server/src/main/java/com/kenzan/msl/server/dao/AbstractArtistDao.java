/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.dao;

import java.util.UUID;

/**
 * Common super class for all Artist DAOs.
 * 
 * NOTE: It would be nice to define here the columns that all Artist DAOs have in common.
 * Unfortunately the Datastax Mapper does not reflect on super classes when mapping result rows to
 * DAOs.
 * 
 * @author billschwanitz
 */
public abstract class AbstractArtistDao extends AbstractDao {

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
