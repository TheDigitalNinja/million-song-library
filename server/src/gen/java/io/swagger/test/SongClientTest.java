package io.swagger.tests;

import io.swagger.api.impl.MslApiResponseMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.Before;
import io.swagger.client.SongClient;
import javax.management.RuntimeErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by anram88 on 11/16/15.
 */
public class SongClientTest {

    private SongClient songClient;
    static Logger logger = Logger.getLogger(SongClientTest.class);

    @Before
    public void init () {
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

    @Test (expected = java.lang.RuntimeException.class)
    public void testGetExceptionIsThrown() {
        logger.debug("songClient.testGetExceptionIsThrown");
        songClient.get("");
    }


    @Test
    public void testBrowse() {
        logger.debug("songClient.testBrowse");
        MslApiResponseMessage songList = songClient.browse("");
        assertEquals("song browse call is successful", "success", songList.getMessage());
    }

    @Test
    public void testBrowseFilteredByFacets() {
        logger.debug("songClient.testBrowseFilteredByFacets");
        MslApiResponseMessage albumList = songClient.browse("3");
        assertEquals("songs browse facet filtered call is successful", "success", albumList.getMessage());
    }

    @Test
    public void testAddSong() {
        logger.debug("songClient.testAddSong");
        MslApiResponseMessage response = songClient.addSong("1", "someToken");
        assertNotNull(response);
        assertEquals("addSong response is successful", "magic!", response.getMessage());
    }

    @Test (expected=java.lang.RuntimeException.class)
    public void testAddSongThrowException (){
        logger.debug("songClient.testAddSongThrowException");
        songClient.addSong("", "");
    }

}
