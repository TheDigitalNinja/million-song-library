/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.bo;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 
 * 
 * @author billschwanitz
 */
public class SongBo extends AbstractBo {
    private UUID songId;
    private String songName;
    private String genre;
    private Integer duration;
    private BigDecimal danceability;
    private BigDecimal songHotttnesss;
    private Integer year;
    private Integer averageRating;
    private Integer personalRating;
    private String imageLink;
    private UUID artistId;
    private UUID artistMbid;
    private String artistName;
    private UUID albumId;
    private String albumName;
    private boolean isInMyLibrary;
    private String favoritesTimestamp;

    /**
     * @return the songId
     */
    public UUID getSongId() {
        return songId;
    }

    /**
     * @param songId the songId to set
     */
    public void setSongId(UUID songId) {
        this.songId = songId;
    }

    /**
     * @return the songName
     */
    public String getSongName() {
        return songName;
    }

    /**
     * @param songName the songName to set
     */
    public void setSongName(String songName) {
        this.songName = songName;
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
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return the danceability
     */
    public BigDecimal getDanceability() {
        return danceability;
    }

    /**
     * @param danceability the danceability to set
     */
    public void setDanceability(BigDecimal danceability) {
        this.danceability = danceability;
    }

    /**
     * @return the songHotttnesss
     */
    public BigDecimal getSongHotttnesss() {
        return songHotttnesss;
    }

    /**
     * @param songHotttnesss the songHotttnesss to set
     */
    public void setSongHotttnesss(BigDecimal songHotttnesss) {
        this.songHotttnesss = songHotttnesss;
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
     * @return the personalRating
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
     * @return the artistMbid
     */
    public UUID getArtistMbid() {
        return artistMbid;
    }

    /**
     * @param artistMbid UUID
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
     * @return boolean
     */
    public boolean isInMyLibrary() {
        return isInMyLibrary;
    }

    /**
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
