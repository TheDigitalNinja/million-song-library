package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.NewCookie;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArtistClientTest {

    private ArtistClient artistClient = new ArtistClient();;
    static Logger logger = Logger.getLogger(ArtistClientTest.class);
    
    @Before
    public void init() {
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testGet() {
        logger.debug("ArtistClient.testGet");
        MslApiResponseMessage artist = artistClient.get(ClientConstants.TEST_ARTIST_ID);
        assertNotNull(artist);
        assertNotNull(artist.getData());
        assertEquals("artist get call is successful", "success", artist.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testGetExceptionIsThrown() {
        logger.debug("ArtistClient.testGetExceptionIsThrown");
        artistClient.get("");
    }

    @Test
    public void testBrowse() {
        logger.debug("ArtistClient.testBrowse");
        MslApiResponseMessage artistList = artistClient.browse(ClientConstants.PAGE_SIZE);
        assertNotNull(artistList);
        assertNotNull(artistList.getData());
        assertEquals("artist browse call is successful", "success", artistList.getMessage());
    }

    @Test
    public void testRateArtist() {
        logger.debug("ArtistClient.testRateArtist");
        NewCookie cookie = new NewCookie("sessionToken", ClientConstants.TEST_TOKEN);
        MslApiResponseMessage response = artistClient.rateArtist(ClientConstants.TEST_ARTIST_ID, 3, cookie.toString());
        assertNotNull(response);
        assertEquals("rateArtist response is successful", "magic!", response.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testRateArtistThrowException() {
        logger.debug("ArtistClient.testRateArtistThrowException");
        artistClient.rateArtist(ClientConstants.TEST_ARTIST_ID, 3, "");
    }
}
