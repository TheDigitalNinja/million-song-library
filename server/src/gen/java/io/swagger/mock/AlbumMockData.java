package io.swagger.mock;

import io.swagger.api.factories.FacetServiceFactory;
import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anram88 on 10/21/15.
 */
public class AlbumMockData {

    public AlbumList albumList;

    public AlbumMockData() {

        this.albumList = new AlbumList();
        List<AlbumInfo> albums = new ArrayList<AlbumInfo>();

        AlbumInfo albumInfoMock1 = new AlbumInfo();
        albumInfoMock1.setAlbumId("1");
        albumInfoMock1.setAlbumName("Alien Love Secrets");
        albumInfoMock1.setArtistId("1");
        albumInfoMock1.setArtistName("Vai, Steve");
        albumInfoMock1.setGenre("Rock");
        albumInfoMock1.setYear(new BigDecimal("1115.37"));
        albumInfoMock1.setAverageRating(new BigDecimal("4.5"));
        albumInfoMock1.setPersonalRating(new BigDecimal("3.5"));
        albumInfoMock1.setImageLink("https://upload.wikimedia.org/wikipedia/en/6/6d/SteveVaiAlienLoveSecrets.jpg");
        albums.add(albumInfoMock1);

        AlbumInfo albumInfoMock2 = new AlbumInfo();
        albumInfoMock2.setAlbumId("2");
        albumInfoMock2.setAlbumName("The Wall");
        albumInfoMock2.setArtistId("2");
        albumInfoMock2.setArtistName("Pink Floyd");
        albumInfoMock2.setGenre("Rock");
        albumInfoMock2.setYear(new BigDecimal("1115.37"));
        albumInfoMock2.setAverageRating(new BigDecimal("3.5"));
        albumInfoMock2.setPersonalRating(new BigDecimal("2.5"));
        albumInfoMock2.setImageLink("http://wordsushi.com/wp-content/uploads/2012/11/The+Wall++high+resolution+png.png");
        albums.add(albumInfoMock2);

        AlbumInfo albumInfoMock3 = new AlbumInfo();
        albumInfoMock3.setAlbumId("3");
        albumInfoMock3.setAlbumName("Farewell song");
        albumInfoMock3.setArtistId("3");
        albumInfoMock3.setArtistName("Janis Joplin");
        albumInfoMock3.setGenre("Rock and Roll");
        albumInfoMock3.setYear(new BigDecimal("1115.37"));
        albumInfoMock3.setAverageRating(new BigDecimal("3.7"));
        albumInfoMock3.setPersonalRating(new BigDecimal("2.7"));
        albumInfoMock3.setImageLink("https://upload.wikimedia.org/wikipedia/en/0/0f/FSJanis.jpg");
        albums.add(albumInfoMock3);

        AlbumInfo albumInfoMock4 = new AlbumInfo();
        albumInfoMock4.setAlbumId("4");
        albumInfoMock4.setAlbumName("Led Zeppelin III");
        albumInfoMock4.setArtistId("4");
        albumInfoMock4.setArtistName("Led Zeppelin");
        albumInfoMock4.setGenre("Rock and Roll");
        albumInfoMock4.setYear(new BigDecimal("1115.37"));
        albumInfoMock4.setAverageRating(new BigDecimal("4.7"));
        albumInfoMock4.setPersonalRating(new BigDecimal("5"));
        albumInfoMock4.setImageLink("https://upload.wikimedia.org/wikipedia/en/5/5f/Led_Zeppelin_-_Led_Zeppelin_III.png");
        albums.add(albumInfoMock4);

        AlbumInfo albumInfoMock5 = new AlbumInfo();
        albumInfoMock5.setAlbumId("5");
        albumInfoMock5.setAlbumName("Uncle Jam Wants You");
        albumInfoMock5.setArtistId("5");
        albumInfoMock5.setArtistName("Funkadelic");
        albumInfoMock5.setGenre("Funk");
        albumInfoMock5.setYear(new BigDecimal("1115.37"));
        albumInfoMock5.setAverageRating(new BigDecimal("2.7"));
        albumInfoMock5.setPersonalRating(new BigDecimal("1115.37"));
        albumInfoMock5.setImageLink("https://upload.wikimedia.org/wikipedia/en/a/ad/UncleJamWantsYou.jpg");
        albums.add(albumInfoMock5);

        AlbumInfo albumInfoMock6 = new AlbumInfo();
        albumInfoMock6.setAlbumId("6");
        albumInfoMock6.setAlbumName("Rainbow Connection");
        albumInfoMock6.setArtistId("6");
        albumInfoMock6.setArtistName("Willie Nelson");
        albumInfoMock6.setGenre("Country");
        albumInfoMock6.setYear(new BigDecimal("1115.37"));
        albumInfoMock6.setAverageRating(new BigDecimal("1.7"));
        albumInfoMock6.setPersonalRating(new BigDecimal("1115.37"));
        albumInfoMock6.setImageLink("https://upload.wikimedia.org/wikipedia/en/7/7c/Willie-Nelson-Rainbow-Connection.jpg");
        albums.add(albumInfoMock6);

        albumList.setAlbums(albums);
    }

    public AlbumInfo getAlbum(String albumId) {
        for (AlbumInfo album : albumList.getAlbums()) {
            if (albumId.equals(album.getAlbumId())) {
                return album;
            }
        }
        return new AlbumInfo();
    }

    public AlbumList browseAlbums(String pagingState, Integer items, String facetList, String sortFields) {

        List<AlbumInfo> browsedAlbums = albumList.getAlbums();

        if (pagingState != null && !pagingState.isEmpty()) {
            // TODO implement pagination
            System.out.println("Paging State: " + pagingState);
        }

        if (facetList != null && facetList.length() > 0) {

            //TODO replace reference to mock data
            FacetMockData facetMockData = new FacetMockData();

            System.out.println("Filtering by facet(s): " + facetList);

            List<AlbumInfo> pivotAlbums = browsedAlbums;
            browsedAlbums = new ArrayList<AlbumInfo>();

            String[] facets = facetList.split(",");
            for (String facet : facets) {
                //TODO replace reference to mock data
                boolean isRatingFacet = FacetServiceFactory.isRatingFacet(facet, facetMockData.mockFacets);
                if (isRatingFacet) {
                    browsedAlbums = FacetServiceFactory.filterAlbumByRatingFacet(pivotAlbums, facet);
                } else {
                    for (AlbumInfo album : pivotAlbums) {
                        if (FacetServiceFactory.getFacet(facet, facetMockData.mockFacets) != null) {
                            String facetName = FacetServiceFactory.getFacet(facet, facetMockData.mockFacets).getName();
                            if (album.getGenre().equals(facetName)) {
                                browsedAlbums.add(album);
                            }
                        }
                    }
                }
            }
        }

        // TODO if no items are provided should return 25 results
        if (items != null && items > 0) {
            List<AlbumInfo> pivotAlbums = browsedAlbums;
            browsedAlbums = new ArrayList<AlbumInfo>();
            for (int i = 0; i < items && i < pivotAlbums.size(); i++) {
                browsedAlbums.add(pivotAlbums.get(i));
            }
        }

        if (sortFields != null && !sortFields.isEmpty()) {
            //TODO implement sorting by sortFields
            System.out.println("Sorting results by: " + sortFields);
        }

        AlbumList results = new AlbumList();
        results.setAlbums(browsedAlbums);

        return results;
    }
}
