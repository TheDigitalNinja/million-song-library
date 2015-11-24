package io.swagger.test;

import io.swagger.api.impl.MslApiResponseMessage;
import io.swagger.api.impl.MslSessionToken;
import io.swagger.client.AlbumClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.NewCookie;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        MslApiResponseMessage album = albumClient.get("1");
        assertNotNull(album);
        assertEquals("album get call is successful", "success", album.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testGetExceptionIsThrown() {
        logger.debug("AlbumClient.testGetExceptionIsThrown");
        albumClient.get("");
    }

    @Test
    public void testBrowse() {
        logger.debug("AlbumClient.testBrowse");
        MslApiResponseMessage albumList = albumClient.browse("", "10");
        assertNotNull(albumList);
        assertEquals("album browse call is successful", "success", albumList.getMessage());
    }

    @Test
    public void testBrowseFilteredByFacets() {
        logger.debug("AlbumClient.testBrowseFilteredByFacets");
        MslApiResponseMessage albumList = albumClient.browse("3", "10");
        assertNotNull(albumList);
        assertEquals("album browse facet filtered call is successful", "success", albumList.getMessage());
    }

    @Test
    public void testAddAlbum(){
        logger.debug("AlbumClient.testAddAlbum");
        NewCookie cookie = new NewCookie("sessionToken", "someToken");
        MslApiResponseMessage response = albumClient.addAlbum("1", cookie.toString());
        assertNotNull(response);
        assertEquals("addAlbum response is successful", "magic!", response.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testAddAlbumThrowException () {
        logger.debug("AlbumClient.testAddAlbumThrowException");
        albumClient.addAlbum("1", "");
    }

    @Test
    public void testRateAlbum () {
        logger.debug("AlbumClient.testRateAlbum");
        NewCookie cookie = new NewCookie("sessionToken", "someToken");
        MslApiResponseMessage response = albumClient.rateAlbum("1", new BigDecimal("4"), cookie.toString());
        assertNotNull(response);
        assertEquals("rateAlbum response is successful", "magic!", response.getMessage());
    }

    @Test (expected = java.lang.RuntimeException.class)
    public void testRateAlbumThrowException () {
        logger.debug("AlbumClient.testRateAlbumThrowException");
        albumClient.rateAlbum("1", new BigDecimal("3"), "");
    }
}
