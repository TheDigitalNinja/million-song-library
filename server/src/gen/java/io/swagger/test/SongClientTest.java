package io.swagger.tests;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.Before;
import io.swagger.client.SongClient;
import javax.ws.rs.core.Response;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by anram88 on 11/16/15.
 */
public class SongClientTest {

    private SongClient sonClient;
    static Logger logger = Logger.getLogger(SongClientTest.class);

    @Before
    public void init () {
        sonClient = new SongClient();
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testGet() {
        logger.debug("songClient.testGet");
        Response song = sonClient.get("1");
        assertEquals("song get call is successful", song.getStatus(), 200);
    }

    @Test
    public void testBrowse() {
        logger.debug("songClient.testBrowse");
        Response songList = sonClient.browse();
        assertEquals("song browse call is successful", songList.getStatus(), 200);
    }

}
