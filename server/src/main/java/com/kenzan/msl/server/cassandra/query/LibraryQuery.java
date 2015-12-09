package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LibraryQuery {

    /**
     * Retrieves the MyLibrary object with contained albums artist and songs
     *
     * @param queryAccessor datastax QueryAccessor object
     * @param sessionToken uuid of authenticated user
     * @return MyLibrary
     */
    public static MyLibrary get(final QueryAccessor queryAccessor, final MappingManager manager,
                                final String sessionToken) {
        MyLibrary myLibrary = new MyLibrary();
        myLibrary.setAlbums(getMyLibraryAlbums(queryAccessor, manager, sessionToken));
        myLibrary.setArtists(getMyLibraryArtists(queryAccessor, manager, sessionToken));
        myLibrary.setSongs(getMyLibrarySongs(queryAccessor, manager, sessionToken));

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
        switch ( contentType ) {
            case "Artist":
                Optional<ArtistBo> optArtistBo = ArtistInfoQuery.get(queryAccessor, manager, null, UUID.fromString(id));
                if ( optArtistBo.isPresent() ) {
                    try {
                        queryAccessor.addLibraryArtist(UUID.fromString(sessionToken), contentType, new Date(),
                                                       optArtistBo.get().getArtistId(), optArtistBo.get()
                                                           .getArtistMbid(), optArtistBo.get().getArtistName());
                    }
                    catch ( Exception error ) {
                        throw error;
                    }
                }
                else {
                    throw new RuntimeErrorException(new Error("Unable to retrieve artist"));
                }
                break;
            case "Album":
                Optional<AlbumBo> optAlbumBo = AlbumInfoQuery.get(queryAccessor, manager, null, UUID.fromString(id));
                if ( optAlbumBo.isPresent() ) {
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
                else {
                    throw new RuntimeErrorException(new Error("Unable to retrieve album"));
                }
                break;
            case "Song":
                Optional<SongBo> optSongBo = SongInfoQuery.get(queryAccessor, manager, null, UUID.fromString(id));
                if ( optSongBo.isPresent() ) {
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
                else {
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
}
