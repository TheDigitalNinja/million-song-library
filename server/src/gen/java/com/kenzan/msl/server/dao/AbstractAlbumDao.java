/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.dao;

import com.datastax.driver.mapping.annotations.Column;

import java.util.UUID;

/**
 *
 *
 * @author billschwanitz
 */
public abstract class AbstractAlbumDao extends AbstractDao {
	@Column (name = "album_id")
	private UUID albumId;
	@Column (name = "album_name")
	private String albumName;
	@Column (name = "album_year")
	private int albumYear;
	@Column (name = "artist_id")
	private UUID artistId;
	@Column (name = "artist_name")
	private String artistName;
	@Column (name = "artist_mbid")
	private UUID artistMbid;
	
	/**
	 * @return the albumId
	 */
	public UUID getAlbumId() {
		return albumId;
	}
	/**
	 * @param albumId the albumId to set
	 */
	public void setAlbumId(UUID albumId) {
		this.albumId = albumId;
	}
	/**
	 * @return the albumName
	 */
	public String getAlbumName() {
		return albumName;
	}
	/**
	 * @param albumName the albumName to set
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	/**
	 * @return the albumYear
	 */
	public int getAlbumYear() {
		return albumYear;
	}
	/**
	 * @param albumYear the albumYear to set
	 */
	public void setAlbumYear(int albumYear) {
		this.albumYear = albumYear;
	}
	/**
	 * @return the artistId
	 */
	public UUID getArtistId() {
		return artistId;
	}
	/**
	 * @param artistId the artistId to set
	 */
	public void setArtistId(UUID artistId) {
		this.artistId = artistId;
	}
	/**
	 * @return the artistName
	 */
	public String getArtistName() {
		return artistName;
	}
	/**
	 * @param artistName the artistName to set
	 */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	/**
	 * @return the artistMbid
	 */
	public UUID getArtistMbid() {
		return artistMbid;
	}
	/**
	 * @param artistMbid the artistMbid to set
	 */
	public void setArtistMbid(UUID artistMbid) {
		this.artistMbid = artistMbid;
	}

}
