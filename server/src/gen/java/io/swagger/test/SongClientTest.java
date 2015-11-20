package io.swagger.tests;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.Before;
import io.swagger.client.SongClient;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by anram88 on 11/16/15.
 */
public class SongClientTest {

    private SongClient songClient;
    static Logger logger = Logger.getLogger(SongClientTest.class);

    @Before
    public void init () {
        songClient = new SongClient();
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testGet() {
        logger.debug("songClient.testGet");
        Response song = songClient.get("1");
        assertEquals("song get call is successful", 200, song.getStatus());
    }

    @Test
    public void testBrowse() {
        logger.debug("songClient.testBrowse");
        Response songList = songClient.browse("");
        assertEquals("song browse call is successful", 200, songList.getStatus());
    }

    @Test
    public void testBrowseFilteredByFacets() {
        logger.debug("songClient.testBrowseFilteredByFacets");
        Response albumList = songClient.browse("3");
        assertEquals("songs browse facet filtered call is successful", 200, albumList.getStatus());
    }

    @Test
    public void testAddSong() {
        logger.debug("songClient.testAddSong");
        Response response = songClient.addSong("1", "someToken");
        assertEquals("addSong response is successful", 200, response.getStatus());
    }

}
