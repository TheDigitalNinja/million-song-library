package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.junit.Test;
import org.junit.Before;

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
        logger.debug("SongClient.testGet");
        MslApiResponseMessage song = songClient.get(ClientConstants.TEST_SONG_ID);
        assertNotNull(song);
        assertNotNull(song.getData());
        assertEquals("song get call is successful", "success", song.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testGetExceptionIsThrown() {
        logger.debug("SongClient.testGetExceptionIsThrown");
        songClient.get("");
    }

    @Test
    public void testBrowse() {
        String PAGE_SIZE = "12";
        logger.debug("songClient.testBrowse");
        MslApiResponseMessage songList = songClient.browse(PAGE_SIZE);
        assertNotNull(songList);
        assertNotNull(songList.getData());
        assertEquals("song browse call is successful", "success", songList.getMessage());
    }

    @Test
    public void testRateSong() {
        logger.debug("songClient.testRateSong");
        MslApiResponseMessage response = songClient.rateSong(ClientConstants.TEST_SONG_ID, 2, ClientConstants.TEST_TOKEN);
        assertNotNull(response);
        assertEquals("rateSong response is successful", "magic!", response.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testRateSongThrowException() {
        logger.debug("songClient.testRateSongThrowException");
        songClient.rateSong(ClientConstants.TEST_SONG_ID, 3, "");
    }

}
