package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.NewCookie;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AlbumClientTest {

    private AlbumClient albumClient;
    static Logger logger = Logger.getLogger(AlbumClientTest.class);

    private final String PAGE_SIZE = "10";
    private final String TEST_TOKEN = "2883607a-176d-4729-a20b-ec441c285afb";
    private final String TEST_ALBUM_ID = "389f9181-99f9-4377-9114-c63b53245355";

    @Before
    public void init() {
        albumClient = new AlbumClient();
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testGet() {
        logger.debug("AlbumClient.testGet");
        MslApiResponseMessage album = albumClient.get(TEST_ALBUM_ID);
        assertNotNull(album);
        assertNotNull(album.getData());
        assertEquals("album get call is successful", "success", album.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testGetExceptionIsThrown() {
        logger.debug("AlbumClient.testGetExceptionIsThrown");
        albumClient.get("");
    }

    @Test
    public void testBrowse() {
        logger.debug("AlbumClient.testBrowse");
        MslApiResponseMessage albumList = albumClient.browse(PAGE_SIZE);
        assertNotNull(albumList);
        assertNotNull(albumList.getData());
        assertEquals("album browse call is successful", "success", albumList.getMessage());
    }

    @Test
    public void testRateAlbum() {
        logger.debug("AlbumClient.testRateAlbum");
        NewCookie cookie = new NewCookie("sessionToken", TEST_TOKEN);
        MslApiResponseMessage response = albumClient.rateAlbum(TEST_ALBUM_ID, 4, cookie.toString());
        assertNotNull(response);
        assertEquals("rateAlbum response is successful", "magic!", response.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testRateAlbumThrowException() {
        logger.debug("AlbumClient.testRateAlbumThrowException");
        albumClient.rateAlbum(TEST_ALBUM_ID, 3, "");
    }
}
