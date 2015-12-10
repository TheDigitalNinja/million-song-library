package com.kenzan.msl.server.dao;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.Date;
import java.util.UUID;

@Table(name = "artists_by_user")
public class ArtistsByUserDao {

    @PartitionKey(value = 0)
    @Column(name = "user_id")
    private UUID userId;
    @PartitionKey(value = 1)
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "favorites_timestamp")
    private Date favoritesTimestamp;
    @Column(name = "artist_id")
    private UUID artistId;
    @Column(name = "artist_mbid")
    private UUID artistMbid;
    @Column(name = "artist_name")
    private String artistName;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getFavoritesTimestamp() {
        return favoritesTimestamp;
    }

    public void setFavoritesTimestamp(Date favoritesTimestamp) {
        this.favoritesTimestamp = favoritesTimestamp;
    }

    public UUID getArtistId() {
        return artistId;
    }

    public void setArtistId(UUID artistId) {
        this.artistId = artistId;
    }

    public UUID getArtistMbid() {
        return artistMbid;
    }

    public void setArtistMbid(UUID artistMbid) {
        this.artistMbid = artistMbid;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
