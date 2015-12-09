package com.kenzan.msl.server.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import java.util.Date;
import java.util.UUID;

@Accessor
public interface QueryAccessor {
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    public ResultSet logIn(@Param("username") String username);

    @Query("SELECT * FROM songs_artist_by_album WHERE album_id = :album_id")
    public ResultSet songsArtistByAlbum(@Param("album_id") UUID album_id);

    @Query("SELECT * FROM songs_albums_by_artist WHERE artist_id = :artist_id")
    public ResultSet songsAlbumsByArtist(@Param("artist_id") UUID artist_id);

    @Query("SELECT * FROM album_artist_by_song WHERE song_id = :song_id")
    public ResultSet albumArtistBySong(@Param("song_id") UUID song_id);

    @Query("SELECT * FROM average_ratings WHERE content_id = :content_id AND content_type = 'Album'")
    public ResultSetFuture albumAverageRating(@Param("content_id") UUID content_id);

    @Query("SELECT * FROM user_data_by_user WHERE user_id = :user_id AND content_id = :content_id AND content_type = 'Album'")
    public ResultSetFuture albumUserRating(@Param("user_id") UUID user_id, @Param("content_id") UUID content_id);

    @Query("SELECT * FROM average_ratings WHERE content_id = :content_id AND content_type = 'Artist'")
    public ResultSetFuture artistAverageRating(@Param("content_id") UUID content_id);

    @Query("SELECT * FROM user_data_by_user WHERE user_id = :user_id AND content_id = :content_id AND content_type = 'Artist'")
    public ResultSetFuture artistUserRating(@Param("user_id") UUID user_id, @Param("content_id") UUID content_id);

    @Query("SELECT * FROM average_ratings WHERE content_id = :content_id AND content_type = 'Song'")
    public ResultSetFuture songAverageRating(@Param("content_id") UUID content_id);

    @Query("SELECT * FROM user_data_by_user WHERE user_id = :user_id AND content_id = :content_id AND content_type = 'Song'")
    public ResultSetFuture songUserRating(@Param("user_id") UUID user_id, @Param("content_id") UUID content_id);

    /**
     * Queries used by Paginator
     */

    public static String FACETED_ALBUMS_QUERY = "SELECT * FROM albums_by_facet WHERE facet_name = :facet_name AND content_type = 'Album'";

    @Query(FACETED_ALBUMS_QUERY)
    public Statement albumsByFacet(@Param("facet_name") String facet_name);

    public static String FACETED_ARTISTS_QUERY = "SELECT * FROM artists_by_facet WHERE facet_name = :facet_name AND content_type = 'Artist'";

    @Query(FACETED_ARTISTS_QUERY)
    public Statement artistsByFacet(@Param("facet_name") String facet_name);

    public static String FACETED_SONGS_QUERY = "SELECT * FROM songs_by_facet WHERE facet_name = :facet_name AND content_type = 'Song'";

    @Query(FACETED_SONGS_QUERY)
    public Statement songsByFacet(@Param("facet_name") String facet_name);

    public static String FEATURED_ALBUMS_QUERY = "SELECT * FROM featured_albums WHERE hotness_bucket = 'Hotness01' AND content_type = 'Album'";

    @Query(FEATURED_ALBUMS_QUERY)
    public Statement featuredAlbums();

    public static String FEATURED_ARTISTS_QUERY = "SELECT * FROM featured_artists WHERE hotness_bucket = 'Hotness01' AND content_type = 'Artist'";

    @Query(FEATURED_ARTISTS_QUERY)
    public Statement featuredArtists();

    public static String FEATURED_SONGS_QUERY = "SELECT * FROM featured_songs WHERE hotness_bucket = 'Hotness01' AND content_type = 'Song'";

    @Query(FEATURED_SONGS_QUERY)
    public Statement featuredSongs();

    /**
     * Library queries
     */

    @Query("SELECT * FROM songs_by_user WHERE user_id = :user_id AND content_type = 'Song'")
    public ResultSet songsByUser(@Param("user_id") UUID user_id);

    @Query("SELECT * FROM albums_by_user WHERE user_id = :user_id AND content_type = 'Album'")
    public ResultSet albumsByUser(@Param("user_id") UUID user_id);

    @Query("SELECT * FROM artists_by_user WHERE user_id = :user_id AND content_type = 'Artist'")
    public ResultSet artistsByUser(@Param("user_id") UUID user_id);

    @Query("INSERT INTO artists_by_user " + "(user_id, content_type, favorites_timestamp, "
        + "artist_id, artist_mbid, artist_name ) " + "VALUES " + "(:user_id,:content_type,:favorites_timestamp,"
        + ":artist_id, :artist_mbid, :artist_name)")
    public void addLibraryArtist(@Param("user_id") UUID user_id, @Param("content_type") String content_type,
                                 @Param("favorites_timestamp") Date favorites_timestamp,
                                 @Param("artist_id") UUID artist_id, @Param("artist_mbid") UUID artist_mbid,
                                 @Param("artist_name") String artist_name);

    @Query("INSERT INTO albums_by_user " + "(user_id, content_type, favorites_timestamp, "
        + "album_id, album_name, album_year, " + "artist_id, artist_mbid, artist_name ) " + "VALUES "
        + "(:user_id,:content_type,:favorites_timestamp," + ":album_id, :album_name, :album_year,"
        + ":artist_id, :artist_mbid, :artist_name)")
    public void addLibraryAlbum(@Param("user_id") UUID user_id, @Param("content_type") String content_type,
                                @Param("favorites_timestamp") Date favorites_timestamp,
                                @Param("album_id") UUID album_id, @Param("album_name") String album_name,
                                @Param("album_year") Integer album_year, @Param("artist_id") UUID artist_id,
                                @Param("artist_mbid") UUID artist_mbid, @Param("artist_name") String artist_name);

    @Query("INSERT INTO songs_by_user " + "(user_id,content_type,favorites_timestamp, "
        + "album_id, album_name, album_year, " + "artist_id, artist_mbid, artist_name, "
        + "song_id, song_duration, song_name) " + "VALUES " + "(:user_id,:content_type,:favorites_timestamp,"
        + ":album_id, :album_name, :album_year," + ":artist_id, :artist_mbid, :artist_name,"
        + ":song_id, :song_duration, :song_name)")
    public void addLibrarySong(@Param("user_id") UUID user_id, @Param("content_type") String content_type,
                               @Param("favorites_timestamp") Date favorites_timestamp, @Param("song_id") UUID song_id,
                               @Param("song_name") String song_name, @Param("song_duration") Integer song_duration,
                               @Param("album_id") UUID album_id, @Param("album_name") String album_name,
                               @Param("album_year") Integer album_year, @Param("artist_id") UUID artist_id,
                               @Param("artist_mbid") UUID artist_mbid, @Param("artist_name") String artist_name);

    @Query("DELETE FROM songs_by_user WHERE user_id=:user_id AND content_type='Song' AND favorites_timestamp = :favorites_timestamp AND song_id=:song_id")
    public void deleteLibrarySong(@Param("song_id") UUID song_id,
                                  @Param("favorites_timestamp") Date favorites_timestamp, @Param("user_id") UUID user_id);

    @Query("DELETE FROM artists_by_user WHERE user_id=:user_id AND content_type='Artist' AND favorites_timestamp = :favorites_timestamp AND artist_id=:artist_id")
    public void deleteLibraryArtist(@Param("artist_id") UUID artist_id,
                                    @Param("favorites_timestamp") Date favorites_timestamp,
                                    @Param("user_id") UUID user_id);

    @Query("DELETE FROM albums_by_user WHERE user_id=:user_id AND content_type='Album' AND favorites_timestamp = :favorites_timestamp AND album_id=:album_id")
    public void deleteLibraryAlbum(@Param("album_id") UUID album_id,
                                   @Param("favorites_timestamp") Date favorites_timestamp,
                                   @Param("user_id") UUID user_id);
}