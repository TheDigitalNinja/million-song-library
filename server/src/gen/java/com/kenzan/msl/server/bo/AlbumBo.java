/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.bo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author billschwanitz
 */
public class AlbumBo extends AbstractBo {
	private String albumId;
	private String albumName;
	private String genre;
	private int year;
	private int averageRating;
	private int personalRating;
	private String imageLink;
	private String artistId;
	private String artistName;
	private List<String> songsList = new ArrayList<String>();
	
	/**
	 * @return the albumId
	 */
	public String getAlbumId() {
		return albumId;
	}
	/**
	 * @param albumId the albumId to set
	 */
	public void setAlbumId(String albumId) {
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
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the averageRating
	 */
	public int getAverageRating() {
		return averageRating;
	}
	/**
	 * @param averageRating the averageRating to set
	 */
	public void setAverageRating(int averageRating) {
		this.averageRating = averageRating;
	}
	/**
	 * @return the personalRanking
	 */
	public int getPersonalRating() {
		return personalRating;
	}
	/**
	 * @param personalRating the personalRating to set
	 */
	public void setPersonalRating(int personalRating) {
		this.personalRating = personalRating;
	}
	/**
	 * @return the imageLink
	 */
	public String getImageLink() {
		return imageLink;
	}
	/**
	 * @param imageLink the imageLink to set
	 */
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	/**
	 * @return the artistId
	 */
	public String getArtistId() {
		return artistId;
	}
	/**
	 * @param artistId the artistId to set
	 */
	public void setArtistId(String artistId) {
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
	 * @return the songsList
	 */
	public List<String> getSongsList() {
		return songsList;
	}
	/**
	 * @param songsList the songsList to set
	 */
	public void setSongsList(List<String> songsList) {
		this.songsList = songsList;
	}
}
