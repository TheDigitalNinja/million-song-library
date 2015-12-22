package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.server.bo.AbstractBo;
import com.kenzan.msl.server.bo.AlbumBo;
import com.kenzan.msl.server.bo.ArtistBo;
import com.kenzan.msl.server.bo.SongBo;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.dao.AlbumsByUserDao;
import com.kenzan.msl.server.dao.ArtistsByUserDao;
import com.kenzan.msl.server.dao.SongsByUserDao;
import com.kenzan.msl.server.translate.Translators;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.MyLibrary;
import io.swagger.model.SongInfo;

import javax.management.RuntimeErrorException;
import java.util.*;

public class LibraryQuery {

    /**
     * Retrieves the MyLibrary object with contained albums artist and songs
     *
     * @param queryAccessor com.datastax.driver.mapping.MappingManager query accessor
     * @param sessionToken uuid of authenticated user
     * @return MyLibrary
     */
    public static MyLibrary get(final QueryAccessor queryAccessor, final MappingManager manager,
                                final String sessionToken) {
        MyLibrary myLibrary = new MyLibrary();
        List<AlbumInfo> albumInfoList = getMyLibraryAlbums(queryAccessor, manager, sessionToken);
        RatingHelper.processAverageAlbumRatings(queryAccessor, manager, albumInfoList);
        RatingHelper
            .processUserAlbumRatingResults(queryAccessor, manager, albumInfoList, UUID.fromString(sessionToken));
        myLibrary.setAlbums(albumInfoList);

        List<ArtistInfo> artistInfoList = getMyLibraryArtists(queryAccessor, manager, sessionToken);
        RatingHelper.processAverageArtistRatings(queryAccessor, manager, artistInfoList);
        RatingHelper.processUserArtistRatingResults(queryAccessor, manager, artistInfoList,
                                                    UUID.fromString(sessionToken));
        myLibrary.setArtists(artistInfoList);

        List<SongInfo> songInfoList = getMyLibrarySongs(queryAccessor, manager, sessionToken);
        RatingHelper.processAverageSongRatings(queryAccessor, manager, songInfoList);
        RatingHelper.processUserSongRatingResults(queryAccessor, manager, songInfoList, UUID.fromString(sessionToken));
        myLibrary.setSongs(songInfoList);

        return myLibrary;
    }

    /**
     * Adds data into a user library
     *
     * @param queryAccessor datastax queryAccesor object
     * @param manager MappingManager object
     * @param id object uuid
     * @param sessionToken authenticated user uuid
     * @param contentType content type (artist|album|song)
     */
    public static void add(final QueryAccessor queryAccessor, final MappingManager manager, final String id,
                           final String sessionToken, final String contentType) {
        MyLibrary myLibrary = get(queryAccessor, manager, sessionToken);
        switch ( contentType ) {
            case "Artist":
                Optional<ArtistBo> optArtistBo = ArtistInfoQuery.get(queryAccessor, manager, null, UUID.fromString(id));
                if ( optArtistBo.isPresent() && !isInLibrary(optArtistBo.get(), myLibrary) ) {
                    try {
                        queryAccessor.addLibraryArtist(UUID.fromString(sessionToken), contentType, new Date(),
                                                       optArtistBo.get().getArtistId(), optArtistBo.get()
                                                           .getArtistMbid(), optArtistBo.get().getArtistName());
                    }
                    catch ( Exception error ) {
                        throw error;
                    }
                }
                else if ( !optArtistBo.isPresent() ) {
                    throw new RuntimeErrorException(new Error("Unable to retrieve artist"));
                }
                break;
            case "Album":
                Optional<AlbumBo> optAlbumBo = AlbumInfoQuery.get(queryAccessor, manager, null, UUID.fromString(id));
                if ( optAlbumBo.isPresent() && !isInLibrary(optAlbumBo.get(), myLibrary) ) {
                    try {
                        queryAccessor.addLibraryAlbum(UUID.fromString(sessionToken), contentType, new Date(),
                                                      optAlbumBo.get().getAlbumId(), optAlbumBo.get().getAlbumName(),
                                                      optAlbumBo.get().getYear(), optAlbumBo.get().getArtistId(),
                                                      optAlbumBo.get().getArtistMbid(), optAlbumBo.get()
                                                          .getArtistName());
                    }
                    catch ( Exception error ) {
                        throw error;
                    }
                }
                else if ( !optAlbumBo.isPresent() ) {
                    throw new RuntimeErrorException(new Error("Unable to retrieve album"));
                }
                break;
            case "Song":
                Optional<SongBo> optSongBo = SongInfoQuery.get(queryAccessor, manager, null, UUID.fromString(id));
                if ( optSongBo.isPresent() && !isInLibrary(optSongBo.get(), myLibrary) ) {
                    try {
                        queryAccessor.addLibrarySong(UUID.fromString(sessionToken), contentType, new Date(), UUID
                            .fromString(id), optSongBo.get().getSongName(), optSongBo.get().getDuration(), optSongBo
                            .get().getAlbumId(), optSongBo.get().getAlbumName(), optSongBo.get().getYear(), optSongBo
                            .get().getArtistId(), optSongBo.get().getArtistMbid(), optSongBo.get().getArtistName());
                    }
                    catch ( Exception error ) {
                        throw error;
                    }
                }
                else if ( !optSongBo.isPresent() ) {
                    throw new RuntimeErrorException(new Error("Unable to retrieve song"));
                }
                break;
        }
    }

    public static void remove(final QueryAccessor queryAccessor, final MappingManager manager, final String id,
                              final String inputTimestamp, final String sessionToken, final String contentType) {

        Date timestamp = new Date(Long.valueOf(inputTimestamp).longValue());

        switch ( contentType ) {
            case "Song":
                try {
                    int initialSongsOnLibrary = getMyLibrarySongs(queryAccessor, manager, sessionToken).size();
                    queryAccessor.deleteLibrarySong(UUID.fromString(id), timestamp, UUID.fromString(sessionToken));
                    if ( initialSongsOnLibrary == getMyLibrarySongs(queryAccessor, manager, sessionToken).size() ) {
                        throw new RuntimeErrorException(new Error("Unable to delete song"));
                    }
                }
                catch ( RuntimeErrorException err ) {
                    throw err;
                }
                break;
            case "Artist":
                try {
                    int initialArtistsOnLibrary = getMyLibraryArtists(queryAccessor, manager, sessionToken).size();
                    queryAccessor.deleteLibraryArtist(UUID.fromString(id), timestamp, UUID.fromString(sessionToken));
                    if ( initialArtistsOnLibrary == getMyLibraryArtists(queryAccessor, manager, sessionToken).size() ) {
                        throw new RuntimeErrorException(new Error("Unable to delete artist"));
                    }
                }
                catch ( RuntimeErrorException err ) {
                    throw err;
                }
                break;
            case "Album":
                try {
                    int initialAlbumsOnLibrary = getMyLibraryAlbums(queryAccessor, manager, sessionToken).size();
                    queryAccessor.deleteLibraryAlbum(UUID.fromString(id), timestamp, UUID.fromString(sessionToken));
                    if ( initialAlbumsOnLibrary == getMyLibraryArtists(queryAccessor, manager, sessionToken).size() ) {
                        throw new RuntimeErrorException(new Error("Unable to delete album"));
                    }
                }
                catch ( RuntimeErrorException err ) {
                    throw err;
                }
                break;
        }
    }

    /**
     * Retrieves the album on a user library
     *
     * @param queryAccessor datastax cassandra QueryAccessor object
     * @param manager datastax MappingManager object
     * @param uuid authenticated user uuid
     * @return List<AlbumInfo>
     */
    private static List<AlbumInfo> getMyLibraryAlbums(final QueryAccessor queryAccessor, final MappingManager manager,
                                                      final String uuid) {
        Result<AlbumsByUserDao> results = manager.mapper(AlbumsByUserDao.class).map(queryAccessor.albumsByUser(UUID
                                                                                        .fromString(uuid)));
        return Translators.translateAlbumsByUserDao(results);
    }

    /**
     * Retrieves the artist list from a respective user library
     *
     * @param queryAccessor datastax QueryAccessor object
     * @param manager datastax MappingManager object
     * @param uuid uuid of authenticated user
     * @return List<ArtistInfo>
     */
    private static List<ArtistInfo> getMyLibraryArtists(final QueryAccessor queryAccessor,
                                                        final MappingManager manager, final String uuid) {
        Result<ArtistsByUserDao> results = manager.mapper(ArtistsByUserDao.class).map(queryAccessor.artistsByUser(UUID
                                                                                          .fromString(uuid)));
        return Translators.translateArtistByUserDao(results);
    }

    /**
     * Retrieves the songs from a user library
     *
     * @param queryAccessor datastax queryAccessor object
     * @param manager datastax MappingManager object
     * @param uuid authenticated user uuid
     * @return List<SongInfo>
     */
    private static List<SongInfo> getMyLibrarySongs(final QueryAccessor queryAccessor, final MappingManager manager,
                                                    final String uuid) {
        Result<SongsByUserDao> results = manager.mapper(SongsByUserDao.class).map(queryAccessor.songsByUser(UUID
                                                                                      .fromString(uuid)));
        return Translators.translateSongsByUserDao(results);
    }

    /**
     * Checks if an object is already on the library and if it is, it attach the timestamp on the
     * abstractBo object
     *
     * @param abstractBo artistInfo, albumInfo or songInfo
     * @param library MyLibrary
     * @return boolean
     */
    public static boolean isInLibrary(AbstractBo abstractBo, MyLibrary library) {
        if ( abstractBo instanceof AlbumBo ) {
            AlbumBo album = (AlbumBo) abstractBo;
            for ( AlbumInfo albumInfo : library.getAlbums() ) {
                if ( albumInfo.getAlbumId().equals(album.getAlbumId().toString()) ) {
                    album.setFavoritesTimestamp(albumInfo.getFavoritesTimestamp());
                    return true;
                }
            }
        }
        else if ( abstractBo instanceof ArtistBo ) {
            ArtistBo artist = (ArtistBo) abstractBo;
            for ( ArtistInfo artistInfo : library.getArtists() ) {
                if ( artistInfo.getArtistId().equals(artist.getArtistId().toString()) ) {
                    artist.setFavoritesTimestamp(artistInfo.getFavoritesTimestamp());
                    return true;
                }
            }
        }
        else if ( abstractBo instanceof SongBo ) {
            SongBo song = (SongBo) abstractBo;
            for ( SongInfo songInfo : library.getSongs() ) {
                if ( songInfo.getSongId().equals(song.getSongId().toString()) ) {
                    song.setFavoritesTimestamp(songInfo.getFavoritesTimestamp());
                    return true;
                }
            }
        }
        return false;
    }
}
