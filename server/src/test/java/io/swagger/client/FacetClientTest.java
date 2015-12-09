package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FacetClientTest {

    private final String PAGE_SIZE = "10";
    private final String ALL_FACETS = "~";
    private final String TEST_FACET = "2";
    private final String ERROR_FACET = " ";

    private FacetClient facetClient;
    static Logger logger = Logger.getLogger(FacetClientTest.class);

    @Before
    public void init() {
        facetClient = new FacetClient();
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testGetFacets() {
        logger.debug("FacetClient.testGetFacets");
        MslApiResponseMessage facetList = facetClient.getFacets(ALL_FACETS);
        assertNotNull(facetList);
        assertEquals("get facets call is successful", "success", facetList.getMessage());
    }

    @Test
    public void testBrowseAlbumFilteredByFacets() {
        logger.debug("FacetClient.testBrowseAlbumFilteredByFacets");
        MslApiResponseMessage albumList = facetClient.browseAlbums(PAGE_SIZE, TEST_FACET);
        assertNotNull(albumList);
        assertEquals("album browse facet filtered call is successful", "success", albumList.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testBrowseAlbumFilteredByFacetsThrowException() {
        logger.debug("FacetClient.testBrowseAlbumFilteredByFacetsThrowException");
        facetClient.browseAlbums(PAGE_SIZE, ERROR_FACET);
    }

    @Test
    public void testBrowseArtistFilteredByFacets() {
        logger.debug("FacetClient.testBrowseArtistFilteredByFacets");
        MslApiResponseMessage artistList = facetClient.browseArtists(PAGE_SIZE, TEST_FACET);
        assertNotNull(artistList);
        assertEquals("artist browse facet filtered call is successful", "success", artistList.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testBrowseArtistFilteredByFacetsThrowException() {
        logger.debug("FacetClient.testBrowseArtistFilteredByFacetsThrowException");
        facetClient.browseArtists(PAGE_SIZE, ERROR_FACET);
    }

    @Test
    public void testBrowseSongFilteredByFacets() {
        logger.debug("FacetClient.testBrowseSongFilteredByFacets");
        MslApiResponseMessage albumList = facetClient.browseSongs(PAGE_SIZE, TEST_FACET);
        assertEquals("songs browse facet filtered call is successful", "success", albumList.getMessage());
    }

    @Test(expected = java.lang.RuntimeException.class)
    public void testBrowseSongFilteredByFacetsThrowException() {
        logger.debug("FacetClient.testBrowseSongFilteredByFacetsThrowException");
        facetClient.browseArtists(PAGE_SIZE, ERROR_FACET);
    }
}
