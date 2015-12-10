/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.dao;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 *
 * @author billschwanitz
 */
@Table(name = "songs_albums_by_artist")
public class SongsAlbumsByArtistDao extends AbstractDao {
    @PartitionKey
    @Column(name = "artist_id")
    private UUID artistId;
    @Column(name = "album_year")
    private int albumYear;
    @Column(name = "album_name")
    private String albumName;
    @Column(name = "album_id")
    private UUID albumId;
    @Column(name = "song_name")
    private String songName;
    @Column(name = "song_id")
    private UUID songId;
    @Column(name = "artist_genres")
    private Set<String> artistGenres;
    @Column(name = "artist_mbid")
    private String artistMbid;
    @Column(name = "artist_name")
    private String artistName;
    @Column(name = "similar_artists")
    private Map<UUID, String> similarArtists;
    @Column(name = "song_duration")
    private int songDuration;

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
     * @return the artistGenres
     */
    public Set<String> getArtistGenres() {
        return artistGenres;
    }

    /**
     * @param artistGenres the artistGenres to set
     */
    public void setArtistGenres(Set<String> artistGenres) {
        this.artistGenres = artistGenres;
    }

    /**
     * @return the artistMbid
     */
    public String getArtistMbid() {
        return artistMbid;
    }

    /**
     * @param artistMbid the artistMbid to set
     */
    public void setArtistMbid(String artistMbid) {
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
     * @return the similarArtists
     */
    public Map<UUID, String> getSimilarArtists() {
        return similarArtists;
    }

    /**
     * @param similarArtists the similarArtists to set
     */
    public void setSimilarArtists(Map<UUID, String> similarArtists) {
        this.similarArtists = similarArtists;
    }

    /**
     * @return the songDuration
     */
    public int getSongDuration() {
        return songDuration;
    }

    /**
     * @param songDuration the songDuration to set
     */
    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }
}
