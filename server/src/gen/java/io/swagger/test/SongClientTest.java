package io.swagger.tests;

import io.swagger.api.impl.MslApiResponseMessage;
import io.swagger.api.impl.MslSessionToken;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.Before;
import io.swagger.client.SongClient;

import javax.management.RuntimeErrorException;
import javax.ws.rs.core.NewCookie;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SongClientTest {

    private SongClient songClient;
    static Logger logger = Logger.getLogger(SongClientTest.class);

    @Before
    public void init() {
        songClient = new SongClient();
        logger.setLevel(Level.INFO);
    }

    @Test
    public void testGet() {
        logger.debug("songClient.testGet");
        MslApiResponseMessage song = songClient.get("1");
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
        MslApiResponseMessage songList = songClient.browse("", "10");
        assertEquals("song browse call is successful", "success", songList.getMessage());
    }

    @Test
    public void testBrowseFilteredByFacets() {
        logger.debug("songClient.testBrowseFilteredByFacets");
        MslApiResponseMessage albumList = songClient.browse("3", "10");
        assertEquals("songs browse facet filtered call is successful", "success", albumList.getMessage());
    }

    @Test
    public void testAddSong() {
        logger.debug("songClient.testAddSong");
        NewCookie cookie = new NewCookie("sessionToken", "someToken");
        MslApiResponseMessage response = songClient.addSong("1", cookie.toString());
        assertNotNull(response);
        assertEquals("addSong response is successful", "magic!", response.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testAddSongThrowException() {
        logger.debug("songClient.testAddSongThrowException");
        songClient.addSong("1", "");
    }

    @Test
    public void testRateSong() {
        logger.debug("songClient.testRateSong");
        NewCookie cookie = new NewCookie("sessionToken", "someToken");
        MslApiResponseMessage response = songClient.rateSong("1", new BigDecimal("2"), cookie.toString());
        assertNotNull(response);
        assertEquals("rateSong response is successful", "magic!", response.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testRateSongThrowException() {
        logger.debug("songClient.testRateSongThrowException");
        songClient.rateSong("1", new BigDecimal("4"), "");
    }

}
