package io.swagger.tests;

import io.swagger.api.impl.MslApiResponseMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.ws.rs.core.NewCookie;
import java.math.BigDecimal;
import io.swagger.client.SongClient;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SongClientTest {

    private final String PAGE_SIZE = "10";

    private SongClient songClient;
    static Logger logger = Logger.getLogger(SongClientTest.class);
    private final String TEST_TOKEN = "2883607a-176d-4729-a20b-ec441c285afb";
    private final String TEST_SONG_ID = "a71d214c-ee76-45c5-a9f3-9b57fc15ef36";

    @Before
    public void init() {
        songClient = new SongClient();
        logger.setLevel(Level.INFO);
    }

    @Test
    public void testGet() {
        logger.debug("songClient.testGet");
        MslApiResponseMessage song = songClient.get(TEST_SONG_ID);
        assertNotNull(song);
        assertEquals("song get call is successful", "success", song.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testGetExceptionIsThrown() {
        logger.debug("songClient.testGetExceptionIsThrown");
        songClient.get("");
    }

    @Test
    public void testBrowse() {
        logger.debug("songClient.testBrowse");
        MslApiResponseMessage songList = songClient.browse(PAGE_SIZE);
        assertEquals("song browse call is successful", "success", songList.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testBrowsExceptionIsThrown() {
        logger.debug("songClient.testBrowsExceptionIsThrown");
        songClient.browse("");
    }

    @Test
    public void testAddSong() {
        logger.debug("songClient.testAddSong");
        NewCookie cookie = new NewCookie("sessionToken", TEST_TOKEN);
        MslApiResponseMessage response = songClient.addSong(TEST_SONG_ID, cookie.toString());
        assertNotNull(response);
        assertEquals("addSong response is successful", "magic!", response.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testAddSongThrowException() {
        logger.debug("songClient.testAddSongThrowException");
        songClient.addSong(TEST_SONG_ID, "");
    }

    @Test
    public void testRateSong() {
        logger.debug("songClient.testRateSong");
        NewCookie cookie = new NewCookie("sessionToken", TEST_TOKEN);
        MslApiResponseMessage response = songClient.rateSong(TEST_SONG_ID, 2, cookie.toString());
        assertNotNull(response);
        assertEquals("rateSong response is successful", "magic!", response.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testRateSongThrowException() {
        logger.debug("songClient.testRateSongThrowException");
        songClient.rateSong(TEST_SONG_ID, 3, "");
    }

}
