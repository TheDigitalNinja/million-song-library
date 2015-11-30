package io.swagger.test;

import io.swagger.api.impl.MslApiResponseMessage;
import io.swagger.client.ArtistClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.NewCookie;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArtistClientTest {

    private final String PAGE_SIZE = "10";

    private ArtistClient artistClient;
    static Logger logger = Logger.getLogger(ArtistClientTest.class);

    private final String TEST_ARTIST_ID = "3f213d36-0e22-45e1-a688-e14fda3bace3";
    private final String TEST_TOKEN = "2883607a-176d-4729-a20b-ec441c285afb";

    @Before
    public void init () {
        artistClient = new ArtistClient();
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testGet() {
        logger.debug("ArtistClient.testGet");
        MslApiResponseMessage artist = artistClient.get(TEST_ARTIST_ID);
        assertNotNull(artist);
        assertEquals("artist get call is successful", "success", artist.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testGetExceptionIsThrown() {
        logger.debug("ArtistClient.testGetExceptionIsThrown");
        artistClient.get("");
    }

    @Test
    public void testBrowse() {
        logger.debug("ArtistClient.testBrowse");
        MslApiResponseMessage artistList = artistClient.browse(PAGE_SIZE);
        assertNotNull(artistList);
        assertEquals("artist browse call is successful", "success", artistList.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testBrowseExceptionIsThrown() {
        logger.debug("ArtistClient.testBrowseExceptionIsThrown");
        artistClient.browse("");
    }

    @Test
    public void testAddArtist(){
        logger.debug("ArtistClient.testAddArtist");
        NewCookie cookie = new NewCookie("sessionToken", TEST_TOKEN);
        MslApiResponseMessage response = artistClient.addArtist("1", cookie.toString());
        assertNotNull(response);
        assertEquals("addArtist response is successful", "magic!", response.getMessage());
    }

    @Test (expected=java.lang.RuntimeException.class)
    public void testAddArtistThrowException (){
        logger.debug("ArtistClient.testAddArtistThrowException");
        artistClient.addArtist(TEST_ARTIST_ID, "");
    }

    @Test
    public void testRateArtist(){
        logger.debug("ArtistClient.testRateArtist");
        NewCookie cookie = new NewCookie("sessionToken", TEST_TOKEN);
        MslApiResponseMessage response = artistClient.rateArtist(TEST_ARTIST_ID, 3, cookie.toString());
        assertNotNull(response);
        assertEquals("rateArtist response is successful", "magic!", response.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testRateArtistThrowException () {
        logger.debug("ArtistClient.testRateArtistThrowException");
        artistClient.rateArtist(TEST_ARTIST_ID, 3, "");
    }
}
