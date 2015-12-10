package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.dao.AlbumsByUserDao;
import com.kenzan.msl.server.dao.ArtistsByUserDao;
import com.kenzan.msl.server.dao.SongsByUserDao;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.MyLibrary;
import io.swagger.model.SongInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        List<AlbumInfo> response = new ArrayList<AlbumInfo>();

        for ( AlbumsByUserDao dao : results ) {
            AlbumInfo albumInfo = new AlbumInfo();
            albumInfo.setArtistName(dao.getArtistName());
            albumInfo.setArtistId(dao.getArtistId().toString());
            // TODO map artist_mbid
            albumInfo.setAlbumId(dao.getAlbumId().toString());
            albumInfo.setArtistName(dao.getAlbumName());
            albumInfo.setYear(dao.getAlbumYear());
            response.add(albumInfo);
        }

        return response;
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

        List<ArtistInfo> response = new ArrayList<ArtistInfo>();

        for ( ArtistsByUserDao dao : results ) {
            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.setArtistName(dao.getArtistName());
            artistInfo.setArtistId(dao.getArtistId().toString());
            // TODO map artist_mbid
            response.add(artistInfo);
        }

        return response;
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

        List<SongInfo> response = new ArrayList<SongInfo>();

        for ( SongsByUserDao dao : results ) {
            SongInfo songInfo = new SongInfo();
            songInfo.setArtistName(dao.getArtistName());
            songInfo.setArtistId(dao.getArtistId().toString());
            // TODO map artist_mbid
            songInfo.setAlbumName(dao.getAlbumName());
            songInfo.setAlbumId(dao.getAlbumId().toString());
            songInfo.setSongId(dao.getSongId().toString());
            songInfo.setSongName(dao.getSongName());
            songInfo.setDuration(dao.getSongDuration());
            songInfo.setYear(dao.getAlbumYear());
            response.add(songInfo);
        }

        return response;
    }

}
