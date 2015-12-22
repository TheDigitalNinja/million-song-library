/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlbumBo extends AbstractBo {
    private UUID albumId;
    private String albumName;
    private String genre;
    private Integer year;
    private Integer averageRating;
    private Integer personalRating;
    private String imageLink;
    private UUID artistId;
    private UUID artistMbid;
    private String artistName;
    private List<String> songsList = new ArrayList<>();
    private boolean isInMyLibrary;
    private String favoritesTimestamp;

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
    public Integer getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return the averageRating
     */
    public Integer getAverageRating() {
        return averageRating;
    }

    /**
     * @param averageRating the averageRating to set
     */
    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * @return the personalRanking
     */
    public Integer getPersonalRating() {
        return personalRating;
    }

    /**
     * @param personalRating the personalRating to set
     */
    public void setPersonalRating(Integer personalRating) {
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
     * @return UUID
     */
    public UUID getArtistMbid() {
        return artistMbid;
    }

    /**
     * @param artistMbid uuid
     */
    public void setArtistMbid(UUID artistMbid) {
        this.artistMbid = artistMbid;
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

    /**
     * @return boolean
     */
    public boolean isInMyLibrary() {
        return isInMyLibrary;
    }

    /**
     *
     * @param isInMyLibrary boolean
     */
    public void setInMyLibrary(boolean isInMyLibrary) {
        this.isInMyLibrary = isInMyLibrary;
    }

    /**
     * @return String
     */
    public String getFavoritesTimestamp() {
        return favoritesTimestamp;
    }

    /**
     * @param favoritesTimestamp String
     */
    public void setFavoritesTimestamp(String favoritesTimestamp) {
        this.favoritesTimestamp = favoritesTimestamp;
    }
}
