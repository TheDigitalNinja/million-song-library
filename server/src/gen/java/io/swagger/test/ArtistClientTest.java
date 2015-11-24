package io.swagger.test;

import io.swagger.api.impl.MslApiResponseMessage;
import io.swagger.client.ArtistClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        MslApiResponseMessage artist = artistClient.get("1");
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
        MslApiResponseMessage artistList = artistClient.browse("");
        assertNotNull(artistList);
        assertEquals("artist browse call is successful", "success", artistList.getMessage());
    }

    @Test
    public void testBrowseFilteredByFacets() {
        logger.debug("ArtistClient.testBrowseFilteredByFacets");
        MslApiResponseMessage artistList = artistClient.browse("3");
        assertNotNull(artistList);
        assertEquals("artist browse facet filtered call is successful", "success", artistList.getMessage());
    }

    @Test
    public void testAddArtist(){
        logger.debug("ArtistClient.testAddArtist");
        MslApiResponseMessage response = artistClient.addArtist("1", "someToken");
        assertNotNull(response);
        assertEquals("addArtist response is successful", "magic!", response.getMessage());
    }

    @Test (expected=java.lang.RuntimeException.class)
    public void testAddArtistThrowException (){
        logger.debug("ArtistClient.testAddArtistThrowException");
        artistClient.addArtist("", "");
    }

    @Test
    public void testRateArtist(){
        logger.debug("ArtistClient.testRateArtist");
        MslApiResponseMessage response = artistClient.rateArtist("1", new BigDecimal("3"), "someToken");
        assertNotNull(response);
        assertEquals("rateArtist response is successful", "magic!", response.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testRateArtistThrowException () {
        logger.debug("ArtistClient.testRateArtistThrowException");
        artistClient.rateArtist("", null, "");
    }
}
