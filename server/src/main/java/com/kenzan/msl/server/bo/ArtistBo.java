/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArtistBo extends AbstractBo {
    private UUID artistId;
    private UUID artistMbid;
    private String artistName;
    private String genre;
    private Integer averageRating;
    private Integer personalRating;
    private String imageLink;
    private List<String> albumsList = new ArrayList<>();
    private List<String> songsList = new ArrayList<>();
    private List<String> similarArtistsList = new ArrayList<>();
    private boolean inMyLibrary;
    private String favoritesTimestamp;

    /**
     * 
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
     * @param artistMbid artist mbid
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
     * @return the albumsList
     */
    public List<String> getAlbumsList() {
        return albumsList;
    }

    /**
     * @param albumsList the albumsList to set
     */
    public void setAlbumsList(List<String> albumsList) {
        this.albumsList = albumsList;
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
     * @return the similarArtistsList
     */
    public List<String> getSimilarArtistsList() {
        return similarArtistsList;
    }

    /**
     * @param similarArtistsList the similarArtistsList to set
     */
    public void setSimilarArtistsList(List<String> similarArtistsList) {
        this.similarArtistsList = similarArtistsList;
    }

    /**
     * @return boolean
     */
    public boolean isInMyLibrary() {
        return inMyLibrary;
    }

    /**
     * @param inMyLibrary boolean
     */
    public void setInMyLibrary(boolean inMyLibrary) {
        this.inMyLibrary = inMyLibrary;
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
