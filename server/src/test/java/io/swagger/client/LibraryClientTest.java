package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.apache.log4j.Level;
import org.junit.Before;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class LibraryClientTest {

    private LibraryClient libraryClient = new LibraryClient();
    static Logger logger = Logger.getLogger(LibraryClientTest.class);

    @Before
    public void init () throws Exception {
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testGetMyLibrary() {
        logger.debug("LibraryClientTest.testGetMyLibrary");
        MslApiResponseMessage myLibrary = libraryClient.getLibrary(ClientConstants.TEST_TOKEN);
        assertNotNull(myLibrary);
        assertNotNull(myLibrary.getData());
        assertEquals("get my library call is successful", "success", myLibrary.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testGetMyLibraryException () {
        logger.debug("LibraryClientTest.testGetMyLibraryException");
        libraryClient.getLibrary("");
    }

    @Ignore
    @Test
    public void testAddSong () {
        logger.debug("LibraryClientTest.testAddSong");
        MslApiResponseMessage addSong = libraryClient.addSongToLibrary(ClientConstants.TEST_SONG_ID, ClientConstants.TEST_TOKEN);
        assertNotNull(addSong);
        assertEquals("add song to library call is successful", "success", addSong.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testAddSongException () {
        logger.debug("LibraryClientTest.testAddSongException");
        libraryClient.addSongToLibrary("someInvalidSongId", "someInvalidToken");
    }

    @Ignore
    @Test
    public void testRemoveSong () {
        logger.debug("LibraryClientTest.testRemoveSong");
        MslApiResponseMessage response = libraryClient.removeSongFromLibrary(ClientConstants.TEST_SONG_ID, ClientConstants.TIMESTAMP, ClientConstants.TEST_TOKEN);
        assertNotNull(response);
        assertEquals("add song to library call is successful", "success", response.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testRemoveSongException () {
        logger.debug("LibraryClientTest.testRemoveSongException");
        libraryClient.removeSongFromLibrary("someInvalidSongId", "",  "someInvalidToken");
    }
    
    // ================================================================================ ARTIST

    @Ignore
    @Test
    public void testAddArtist () {
        logger.debug("LibraryClientTest.testAddArtist");
        MslApiResponseMessage addArtist = libraryClient.addArtistToLibrary(ClientConstants.TEST_ARTIST_ID, ClientConstants.TEST_TOKEN);
        assertNotNull(addArtist);
        assertEquals("add song to library call is successful", "success", addArtist.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testAddArtistException () {
        logger.debug("LibraryClientTest.testAddArtistException");
        libraryClient.addArtistToLibrary("someInvalidArtistId", "someInvalidToken");
    }

    @Ignore
    @Test
    public void testRemoveArtist () {
        logger.debug("LibraryClientTest.testRemoveArtist");
        MslApiResponseMessage response = libraryClient.removeArtistFromLibrary(ClientConstants.TEST_ARTIST_ID, ClientConstants.TIMESTAMP, ClientConstants.TEST_TOKEN);
        assertNotNull(response);
        assertEquals("add song to library call is successful", "success", response.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testRemoveArtistException () {
        logger.debug("LibraryClientTest.testRemoveArtistException");
        libraryClient.removeArtistFromLibrary("someInvalidArtistId", "", "someInvalidToken");
    }
    
    // ======================================================================== ALBUM

    @Ignore
    @Test
    public void testAddAlbum () {
        logger.debug("LibraryClientTest.testAddAlbum");
        MslApiResponseMessage addAlbum = libraryClient.addAlbumToLibrary(ClientConstants.TEST_ALBUM_ID, ClientConstants.TEST_TOKEN);
        assertNotNull(addAlbum);
        assertEquals("add song to library call is successful", "success", addAlbum.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testAddAlbumException () {
        logger.debug("LibraryClientTest.testAddAlbumException");
        libraryClient.addAlbumToLibrary("someInvalidAlbumId", "someInvalidToken");
    }

    @Ignore
    @Test
    public void testRemoveAlbum () {
        logger.debug("LibraryClientTest.testRemoveAlbum");
        MslApiResponseMessage response = libraryClient.removeAlbumFromLibrary(ClientConstants.TEST_ALBUM_ID, ClientConstants.TIMESTAMP, ClientConstants.TEST_TOKEN);
        assertNotNull(response);
        assertEquals("add song to library call is successful", "success", response.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testRemoveAlbumException () {
        logger.debug("LibraryClientTest.testRemoveAlbumException");
        libraryClient.removeAlbumFromLibrary("someInvalidAlbumId", "", "someInvalidToken");
    }


}
