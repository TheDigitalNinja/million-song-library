package io.swagger.test;

import io.swagger.client.AlbumClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;

/**
 * Created by anram88 on 11/19/15.
 */
public class AlbumClientTest {

    private AlbumClient albumClient;
    static Logger logger = Logger.getLogger(AlbumClientTest.class);

    @Before
    public void init () {
        albumClient = new AlbumClient();
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testGet() {
        logger.debug("AlbumClient.testGet");
        Response album = albumClient.get("1");
        assertEquals("album get call is successful", 200, album.getStatus());
    }

    @Test
    public void testBrowse() {
        logger.debug("AlbumClient.testBrowse");
        Response albumList = albumClient.browse("");
        assertEquals("album browse call is successful", 200, albumList.getStatus());
    }

    @Test
    public void testBrowseFilteredByFacets() {
        logger.debug("AlbumClient.testBrowseFilteredByFacets");
        Response albumList = albumClient.browse("3");
        assertEquals("album browse facet filtered call is successful", 200, albumList.getStatus());
    }

    @Test
    public void testAddAlbum(){
        logger.debug("AlbumClient.testAddAlbum");
        Response response = albumClient.addAlbum("1", "someToken");
        assertEquals("addAlbum response is successful", 200, response.getStatus());
    }
}
