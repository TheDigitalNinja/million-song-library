package io.swagger.mock;

import io.swagger.api.factories.FacetServiceFactory;
import io.swagger.model.ArtistList;
import io.swagger.model.ArtistInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anram88 on 10/22/15.
 */
public class ArtistMockData {

    public ArtistList artistList;

    public ArtistMockData() {
        this.artistList = new ArtistList();
        List<ArtistInfo> artists = new ArrayList<ArtistInfo>();

        ArtistInfo artistMockData1 = new ArtistInfo();
        artistMockData1.setArtistId("1");
        artistMockData1.setArtistName("Vai, Steve");
        artistMockData1.setImageLink("https://veggiesrock.files.wordpress.com/2011/07/vai2009_promo11.jpg");
        artistMockData1.setAverageRating(new BigDecimal("4.7"));
        List<String> artistMockAlbums1 = new ArrayList<String>();
        artistMockAlbums1.add("1");
        artistMockData1.setAlbumsList(artistMockAlbums1);
        List<String> artistMockSongs1 = new ArrayList<String>();
        artistMockSongs1.add("1");
        artistMockData1.setGenre("Rock");
        artistMockData1.setSongsList(artistMockSongs1);
        artists.add(artistMockData1);

        ArtistInfo artistMockData2 = new ArtistInfo();
        artistMockData2.setArtistId("2");
        artistMockData2.setArtistName("Pink Floyd");
        artistMockData2.setImageLink("http://www.billboard.com/files/styles/promo_650/public/media/pink-floyd-1973-billboard-650.jpg");
        artistMockData2.setAverageRating(new BigDecimal("4.7"));
        List<String> artistMockAlbums2 = new ArrayList<String>();
        artistMockAlbums2.add("2");
        artistMockData2.setAlbumsList(artistMockAlbums2);
        List<String> artistMockSongs2 = new ArrayList<String>();
        artistMockSongs2.add("2");
        artistMockData2.setGenre("Rock");
        artistMockData2.setSongsList(artistMockSongs2);
        artists.add(artistMockData2);

        ArtistInfo artistMockData3 = new ArtistInfo();
        artistMockData3.setArtistId("3");
        artistMockData3.setArtistName("Janis Joplin");
        artistMockData3.setImageLink("http://www.thisdayinmusic.com/upload/janis_joplin_6727.jpg");
        artistMockData3.setAverageRating(new BigDecimal("3.7"));
        artists.add(artistMockData3);
        List<String> artistMockAlbums3 = new ArrayList<String>();
        artistMockData3.setAlbumsList(artistMockAlbums3);
        List<String> artistMockSongs3 = new ArrayList<String>();
        artistMockSongs3.add("3");
        artistMockData3.setGenre("Rock and Roll");
        artistMockData3.setSongsList(artistMockSongs3);
        artistMockAlbums3.add("3");

        ArtistInfo artistMockData4 = new ArtistInfo();
        artistMockData4.setArtistId("4");
        artistMockData4.setArtistName("Led Zeppelin");
        artistMockData4.setImageLink("http://d2x3wmakafwqf5.cloudfront.net/wordpress/wp-content/blogs.dir/106/files/2014/06/led_zeppelin_wallpaper_blac_and_white.jpg");
        artistMockData4.setAverageRating(new BigDecimal("4.7"));
        List<String> artistMockAlbums4 = new ArrayList<String>();
        artistMockAlbums4.add("4");
        artistMockData4.setAlbumsList(artistMockAlbums4);
        List<String> artistMockSongs4 = new ArrayList<String>();
        artistMockSongs4.add("4");
        artistMockData4.setGenre("Rock and Roll");
        artistMockData4.setSongsList(artistMockSongs4);
        artists.add(artistMockData4);

        ArtistInfo artistMockData5 = new ArtistInfo();
        artistMockData5.setArtistId("5");
        artistMockData5.setArtistName("Funkadelic");
        artistMockData5.setImageLink("http://acerecords.co.uk/images/CDSEW2-055.jpg");
        artistMockData5.setGenre("Funk");
        artistMockData5.setAverageRating(new BigDecimal("1.7"));
        List<String> artistMockAlbums5 = new ArrayList<String>();
        artistMockAlbums5.add("5");
        artistMockData5.setAlbumsList(artistMockAlbums5);
        List<String> artistMockSongs5 = new ArrayList<String>();
        artistMockSongs5.add("5");
        artistMockData5.setSongsList(artistMockSongs5);
        artists.add(artistMockData5);

        ArtistInfo artistMockData6 = new ArtistInfo();
        artistMockData6.setArtistId("6");
        artistMockData6.setArtistName("Willie Nelson");
        artistMockData6.setImageLink("http://kom.net/~dbrick/music/willie/willie2.jpg");
        artistMockData6.setAverageRating(new BigDecimal("1.7"));
        List<String> artistMockAlbums6 = new ArrayList<String>();
        artistMockAlbums6.add("6");
        artistMockData6.setGenre("Country");
        artistMockData6.setAlbumsList(artistMockAlbums6);
        List<String> artistMockSongs6 = new ArrayList<String>();
        artistMockSongs5.add("6");
        artistMockData6.setSongsList(artistMockSongs6);
        artists.add(artistMockData6);

        artistList.setArtists(artists);
    }

    public ArtistInfo getArtist(String artistId) {
        for (ArtistInfo artist : artistList.getArtists()) {
            if (artist.getArtistId().equals(artistId)) {
                return artist;
            }
        }
        return new ArtistInfo();
    }

    public ArtistList browseArtists(String pagingState, Integer items, String facetList, String sortFields) {
        List<ArtistInfo> browsedArtists = artistList.getArtists();

        if (pagingState != null && !pagingState.isEmpty()) {
            // TODO implement pagination
            System.out.println("Paging State: " + pagingState);
        }

        if (facetList != null && facetList.length() > 0) {

            //TODO replace reference to mock data
            FacetMockData facetMockData = new FacetMockData();

            System.out.println("Filtering by facet(s): " + facetList);

            List<ArtistInfo> pivotArtists = browsedArtists;
            browsedArtists = new ArrayList<ArtistInfo>();

            String[] facets = facetList.split(",");
            for (String facet : facets) {
                //TODO replace reference to mock data
                if (FacetServiceFactory.isRatingFacet(facet, facetMockData.mockFacets)) {
                    browsedArtists = FacetServiceFactory.filterArtistByRatingFacet(pivotArtists, facet);
                } else {
                    for (ArtistInfo artist : pivotArtists) {
                        if (FacetServiceFactory.getFacet(facet, facetMockData.mockFacets) != null) {
                            String facetName = FacetServiceFactory.getFacet(facet, facetMockData.mockFacets).getName();
                            if (artist.getGenre().equals(facetName)) {
                                browsedArtists.add(artist);
                            }
                        }
                    }
                }
            }
        }

        // TODO if no items is provided should return 25 results
        if (items != null && items > 0) {
            List<ArtistInfo> pivotArtists = browsedArtists;
            browsedArtists = new ArrayList<ArtistInfo>();
            for (int i = 0; i < items && i < pivotArtists.size(); i++) {
                browsedArtists.add(pivotArtists.get(i));
            }
        }

        if (sortFields != null && !sortFields.isEmpty()) {
            //TODO implement sorting by sortFields
            System.out.println("Sorting results by: " + sortFields);
        }

        ArtistList results = new ArtistList();
        results.setArtists(browsedArtists);

        return results;
    }
}
