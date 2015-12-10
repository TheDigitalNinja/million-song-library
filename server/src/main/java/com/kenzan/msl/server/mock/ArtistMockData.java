package com.kenzan.msl.server.mock;

import io.swagger.api.factories.FacetServiceFactory;
import io.swagger.model.ArtistList;
import io.swagger.model.ArtistInfo;
import java.util.ArrayList;
import java.util.List;

public class ArtistMockData {

    public ArtistList artistList;
    private FacetMockData facetMockData = new FacetMockData();

    public ArtistInfo getArtist(String artistId) {
        for ( ArtistInfo artist : artistList.getArtists() ) {
            if ( artist.getArtistId().equals(artistId) ) {
                return artist;
            }
        }
        return new ArtistInfo();
    }

    private List<ArtistInfo> applyGenreFacet(String[] facets, List<ArtistInfo> artistList) {
        List<ArtistInfo> result = new ArrayList<>();
        boolean hasRatingFacet = false;
        for ( String facet : facets ) {
            if ( !FacetServiceFactory.isRatingFacet(facet, facetMockData.getRatingFacets()) ) {
                hasRatingFacet = true;
                for ( ArtistInfo artist : artistList ) {
                    String facetName = FacetServiceFactory.getFacet(facet, facetMockData.mockFacets).getName();
                    if ( artist.getGenre().equals(facetName) ) {
                        result.add(artist);
                    }
                }
            }
        }

        if ( hasRatingFacet ) {
            return result;
        }

        return artistList;
    }

    private List<ArtistInfo> applyRatingFacet(String[] facets, List<ArtistInfo> artistList) {
        for ( String facet : facets ) {
            if ( FacetServiceFactory.isRatingFacet(facet, facetMockData.getRatingFacets()) ) {
                return FacetServiceFactory.filterArtistByRatingFacet(artistList, facet);
            }
        }
        return artistList;
    }

    public ArtistList browseArtists(String pagingState, Integer items, String facetList) {
        List<ArtistInfo> browsedArtists = artistList.getArtists();

        if ( pagingState != null && !pagingState.isEmpty() ) {
            // TODO implement pagination
            System.out.println("Paging State: " + pagingState);
        }

        if ( facetList != null && facetList.length() > 0 ) {
            System.out.println("Filtering by facet(s): " + facetList);
            String[] facets = facetList.split(",");
            browsedArtists = applyRatingFacet(facets, browsedArtists);
            browsedArtists = applyGenreFacet(facets, browsedArtists);
        }

        // TODO if no items is provided should return 25 results
        if ( items != null && items > 0 ) {
            List<ArtistInfo> pivotArtists = browsedArtists;
            browsedArtists = new ArrayList<>();
            for ( int i = 0; i < items && i < pivotArtists.size(); i++ ) {
                browsedArtists.add(pivotArtists.get(i));
            }
        }

        ArtistList results = new ArtistList();
        results.setArtists(browsedArtists);

        return results;
    }

    public ArtistMockData() {
        this.artistList = new ArtistList();
        List<ArtistInfo> artists = new ArrayList<>();

        ArtistInfo artistMockData1 = new ArtistInfo();
        artistMockData1.setArtistId("1");
        artistMockData1.setArtistName("Vai, Steve");
        artistMockData1.setImageLink("https://veggiesrock.files.wordpress.com/2011/07/vai2009_promo11.jpg");
        artistMockData1.setAverageRating(4);
        List<String> artistMockAlbums1 = new ArrayList<>();
        artistMockAlbums1.add("1");
        artistMockData1.setAlbumsList(artistMockAlbums1);
        List<String> artistMockSongs1 = new ArrayList<>();
        artistMockSongs1.add("1");
        artistMockData1.setGenre("Rock");
        artistMockData1.setSongsList(artistMockSongs1);
        List<String> artistMockData1SimilarArtists = new ArrayList<>();
        artistMockData1SimilarArtists.add("7");
        artistMockData1.setSimilarArtistsList(artistMockData1SimilarArtists);
        artists.add(artistMockData1);

        ArtistInfo artistMockData2 = new ArtistInfo();
        artistMockData2.setArtistId("2");
        artistMockData2.setArtistName("Pink Floyd");
        artistMockData2
            .setImageLink("http://www.billboard.com/files/styles/promo_650/public/media/pink-floyd-1973-billboard-650.jpg");
        artistMockData2.setAverageRating(4);
        List<String> artistMockAlbums2 = new ArrayList<>();
        artistMockAlbums2.add("2");
        artistMockData2.setAlbumsList(artistMockAlbums2);
        List<String> artistMockSongs2 = new ArrayList<>();
        artistMockSongs2.add("2");
        artistMockData2.setGenre("Rock");
        artistMockData2.setSongsList(artistMockSongs2);
        artists.add(artistMockData2);

        ArtistInfo artistMockData3 = new ArtistInfo();
        artistMockData3.setArtistId("3");
        artistMockData3.setArtistName("Janis Joplin");
        artistMockData3.setImageLink("http://www.thisdayinmusic.com/upload/janis_joplin_6727.jpg");
        artistMockData3.setAverageRating(3);
        artists.add(artistMockData3);
        List<String> artistMockAlbums3 = new ArrayList<>();
        artistMockData3.setAlbumsList(artistMockAlbums3);
        List<String> artistMockSongs3 = new ArrayList<>();
        artistMockSongs3.add("3");
        artistMockData3.setGenre("Rock and Roll");
        artistMockData3.setSongsList(artistMockSongs3);
        List<String> artistMockData3SimilarArtists = new ArrayList<>();
        artistMockData3SimilarArtists.add("4");
        artistMockData3.setSimilarArtistsList(artistMockData3SimilarArtists);
        artistMockAlbums3.add("3");

        ArtistInfo artistMockData4 = new ArtistInfo();
        artistMockData4.setArtistId("4");
        artistMockData4.setArtistName("Led Zeppelin");
        artistMockData4
            .setImageLink("http://d2x3wmakafwqf5.cloudfront.net/wordpress/wp-content/blogs.dir/106/files/2014/06/led_zeppelin_wallpaper_blac_and_white.jpg");
        artistMockData4.setAverageRating(4);
        List<String> artistMockAlbums4 = new ArrayList<>();
        artistMockAlbums4.add("4");
        artistMockData4.setAlbumsList(artistMockAlbums4);
        List<String> artistMockSongs4 = new ArrayList<>();
        artistMockSongs4.add("4");
        artistMockData4.setGenre("Rock and Roll");
        artistMockData4.setSongsList(artistMockSongs4);
        List<String> artistMockData4SimilarArtists = new ArrayList<>();
        artistMockData4SimilarArtists.add("3");
        artistMockData4.setSimilarArtistsList(artistMockData4SimilarArtists);
        artists.add(artistMockData4);

        ArtistInfo artistMockData5 = new ArtistInfo();
        artistMockData5.setArtistId("5");
        artistMockData5.setArtistName("Funkadelic");
        artistMockData5.setImageLink("http://acerecords.co.uk/images/CDSEW2-055.jpg");
        artistMockData5.setGenre("Funk");
        artistMockData5.setAverageRating(2);
        List<String> artistMockAlbums5 = new ArrayList<>();
        artistMockAlbums5.add("5");
        artistMockData5.setAlbumsList(artistMockAlbums5);
        List<String> artistMockSongs5 = new ArrayList<>();
        artistMockSongs5.add("5");
        artistMockData5.setSongsList(artistMockSongs5);
        List<String> artistMockData5SimilarArtists = new ArrayList<>();
        artistMockData5SimilarArtists.add("8");
        artistMockData5.setSimilarArtistsList(artistMockData5SimilarArtists);
        artists.add(artistMockData5);

        ArtistInfo artistMockData6 = new ArtistInfo();
        artistMockData6.setArtistId("6");
        artistMockData6.setArtistName("Willie Nelson");
        artistMockData6.setImageLink("http://kom.net/~dbrick/music/willie/willie2.jpg");
        artistMockData6.setAverageRating(1);
        List<String> artistMockAlbums6 = new ArrayList<>();
        artistMockAlbums6.add("6");
        artistMockData6.setGenre("Country");
        artistMockData6.setAlbumsList(artistMockAlbums6);
        List<String> artistMockSongs6 = new ArrayList<>();
        artistMockSongs6.add("6");
        artistMockData6.setSongsList(artistMockSongs6);
        artists.add(artistMockData6);

        ArtistInfo artistMockData7 = new ArtistInfo();
        artistMockData7.setArtistId("7");
        artistMockData7.setArtistName("Joe Satriani");
        artistMockData7.setImageLink("http://www.spirit-of-metal.com/membre_groupe/photo/Joe_Satriani-14020.jpg");
        artistMockData7.setAverageRating(4);
        List<String> artistMockAlbums7 = new ArrayList<>();
        artistMockAlbums7.add("7");
        artistMockAlbums7.add("8");
        artistMockData7.setAlbumsList(artistMockAlbums7);
        List<String> artistMockSongs7 = new ArrayList<>();
        artistMockSongs7.add("7");
        artistMockSongs7.add("8");
        artistMockData7.setGenre("Rock");
        artistMockData7.setSongsList(artistMockSongs7);
        List<String> artistMockData7SimilarArtists = new ArrayList<>();
        artistMockData7SimilarArtists.add("1");
        artistMockData7.setSimilarArtistsList(artistMockData7SimilarArtists);
        artists.add(artistMockData7);

        ArtistInfo artistMockData8 = new ArtistInfo();
        artistMockData8.setArtistId("8");
        artistMockData8.setArtistName("Earth wind and fire");
        artistMockData8
            .setImageLink("http://www.myplay.com/files/imagecache/photo_345_square/files/artist_images/dxc__qp1046245.jpg");
        artistMockData8.setAverageRating(2);
        List<String> artistMockAlbums8 = new ArrayList<>();
        artistMockAlbums8.add("9");
        artistMockData8.setAlbumsList(artistMockAlbums8);
        List<String> artistMockSongs8 = new ArrayList<>();
        artistMockSongs8.add("9");
        artistMockData8.setGenre("Funk");
        artistMockData8.setSongsList(artistMockSongs8);
        List<String> artistMockData8SimilarArtists = new ArrayList<>();
        artistMockData8SimilarArtists.add("5");
        artistMockData8.setSimilarArtistsList(artistMockData8SimilarArtists);
        artists.add(artistMockData8);

        artistList.setArtists(artists);
    }
}
