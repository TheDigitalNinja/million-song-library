package io.swagger.test;

import io.swagger.client.ArtistClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by anram88 on 11/19/15.
 */
public class ArtistClientTest {

    private ArtistClient artistClient;
    static Logger logger = Logger.getLogger(ArtistClientTest.class);

    @Before
    public void init () {
        artistClient = new ArtistClient();
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testGet() {
        logger.debug("ArtistClient.testGet");
        Response artist = artistClient.get("1");
        assertEquals("artist get call is successful", 200, artist.getStatus());
    }

    @Test
    public void testBrowse() {
        logger.debug("ArtistClient.testBrowse");
        Response artistList = artistClient.browse("");
        assertEquals("artist browse call is successful", 200, artistList.getStatus());
    }

    @Test
    public void testBrowseFilteredByFacets() {
        logger.debug("ArtistClient.testBrowseFilteredByFacets");
        Response artistList = artistClient.browse("3");
        assertEquals("artist browse facet filtered call is successful", 200, artistList.getStatus());
    }

    @Test
    public void testAddArtist(){
        logger.debug("ArtistClient.testAddArtist");
        Response response = artistClient.addArtist("1", "someToken");
        assertEquals("addArtist response is successful", 200, response.getStatus());
    }
}
