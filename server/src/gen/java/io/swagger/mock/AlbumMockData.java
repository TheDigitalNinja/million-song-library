package io.swagger.mock;

import io.swagger.api.factories.FacetServiceFactory;
import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlbumMockData {

    public AlbumList albumList;
    private FacetMockData facetMockData = new FacetMockData();

    public AlbumInfo getAlbum(String albumId) {
        for (AlbumInfo album : albumList.getAlbums()) {
            if (albumId.equals(album.getAlbumId())) {
                return album;
            }
        }
        return new AlbumInfo();
    }

    private List<AlbumInfo> applyGenreFacet (String [] facets, List<AlbumInfo> albumList){
        List<AlbumInfo> result = new ArrayList<AlbumInfo>();
        boolean hasRatingFacet = false;
        for (String facet: facets) {
            if (!FacetServiceFactory.isRatingFacet(facet, facetMockData.getRatingFacets())) {
                hasRatingFacet = true;
                for (AlbumInfo artist : albumList) {
                    String facetName = FacetServiceFactory.getFacet(facet, facetMockData.mockFacets).getName();
                    if (artist.getGenre().equals(facetName)) {
                        result.add(artist);
                    }
                }
            }
        }

        if (hasRatingFacet) {
            return result;
        }else {
            return albumList;
        }
    }

    private List<AlbumInfo> applyRatingFacet (String [] facets, List<AlbumInfo> albumList) {
        for (String facet : facets) {
            if (FacetServiceFactory.isRatingFacet(facet, facetMockData.getRatingFacets())) {
                return FacetServiceFactory.filterAlbumByRatingFacet(albumList, facet);
            }
        }
        return albumList;
    }

    public AlbumList browseAlbums(String pagingState, Integer items, String facetList) {

        List<AlbumInfo> browsedAlbums = albumList.getAlbums();

        if (pagingState != null && !pagingState.isEmpty()) {
            // TODO implement pagination
            System.out.println("Paging State: " + pagingState);
        }

        if (facetList != null && facetList.length() > 0) {
            System.out.println("Filtering by facet(s): " + facetList);
            String[] facets = facetList.split(",");
            browsedAlbums = applyRatingFacet(facets, browsedAlbums);
            browsedAlbums = applyGenreFacet(facets, browsedAlbums);
        }

        // TODO if no items are provided should return 25 results
        if (items != null && items > 0) {
            List<AlbumInfo> pivotAlbums = browsedAlbums;
            browsedAlbums = new ArrayList<AlbumInfo>();
            for (int i = 0; i < items && i < pivotAlbums.size(); i++) {
                browsedAlbums.add(pivotAlbums.get(i));
            }
        }

        AlbumList results = new AlbumList();
        results.setAlbums(browsedAlbums);

        return results;
    }

    public AlbumMockData() {

        this.albumList = new AlbumList();
        List<AlbumInfo> albums = new ArrayList<AlbumInfo>();

        AlbumInfo albumInfoMock1 = new AlbumInfo();
        albumInfoMock1.setAlbumId("1");
        albumInfoMock1.setAlbumName("Alien Love Secrets");
        albumInfoMock1.setArtistId("1");
        albumInfoMock1.setArtistName("Vai, Steve");
        albumInfoMock1.setGenre("Rock");
        albumInfoMock1.setYear(new BigDecimal("2000"));
        albumInfoMock1.setAverageRating(new BigDecimal("4"));
        albumInfoMock1.setPersonalRating(new BigDecimal("3"));
        albumInfoMock1.setImageLink("https://upload.wikimedia.org/wikipedia/en/6/6d/SteveVaiAlienLoveSecrets.jpg");
        List<String> album1songsList = new ArrayList<String>();
        album1songsList.add("1");
        albumInfoMock1.setSongsList(album1songsList);
        albums.add(albumInfoMock1);

        AlbumInfo albumInfoMock2 = new AlbumInfo();
        albumInfoMock2.setAlbumId("2");
        albumInfoMock2.setAlbumName("The Wall");
        albumInfoMock2.setArtistId("2");
        albumInfoMock2.setArtistName("Pink Floyd");
        albumInfoMock2.setGenre("Rock");
        albumInfoMock2.setYear(new BigDecimal("1977"));
        albumInfoMock2.setAverageRating(new BigDecimal("4"));
        albumInfoMock2.setPersonalRating(new BigDecimal("4"));
        albumInfoMock2.setImageLink("http://wordsushi.com/wp-content/uploads/2012/11/The+Wall++high+resolution+png.png");
        List<String> album2songsList = new ArrayList<String>();
        album2songsList.add("2");
        albumInfoMock2.setSongsList(album2songsList);
        albums.add(albumInfoMock2);

        AlbumInfo albumInfoMock3 = new AlbumInfo();
        albumInfoMock3.setAlbumId("3");
        albumInfoMock3.setAlbumName("Farewell song");
        albumInfoMock3.setArtistId("3");
        albumInfoMock3.setArtistName("Janis Joplin");
        albumInfoMock3.setGenre("Rock and Roll");
        albumInfoMock3.setYear(new BigDecimal("1965"));
        albumInfoMock3.setAverageRating(new BigDecimal("3"));
        albumInfoMock3.setPersonalRating(new BigDecimal("3"));
        albumInfoMock3.setImageLink("https://upload.wikimedia.org/wikipedia/en/0/0f/FSJanis.jpg");
        List<String> album3songsList = new ArrayList<String>();
        album3songsList.add("3");
        albumInfoMock3.setSongsList(album3songsList);
        albums.add(albumInfoMock3);

        AlbumInfo albumInfoMock4 = new AlbumInfo();
        albumInfoMock4.setAlbumId("4");
        albumInfoMock4.setAlbumName("Led Zeppelin III");
        albumInfoMock4.setArtistId("4");
        albumInfoMock4.setArtistName("Led Zeppelin");
        albumInfoMock4.setGenre("Rock and Roll");
        albumInfoMock4.setYear(new BigDecimal("1975"));
        albumInfoMock4.setAverageRating(new BigDecimal("4"));
        albumInfoMock4.setPersonalRating(new BigDecimal("5"));
        albumInfoMock4.setImageLink("https://upload.wikimedia.org/wikipedia/en/5/5f/Led_Zeppelin_-_Led_Zeppelin_III.png");
        List<String> album4songsList = new ArrayList<String>();
        album4songsList.add("4");
        albumInfoMock4.setSongsList(album4songsList);
        albums.add(albumInfoMock4);

        AlbumInfo albumInfoMock5 = new AlbumInfo();
        albumInfoMock5.setAlbumId("5");
        albumInfoMock5.setAlbumName("Uncle Jam Wants You");
        albumInfoMock5.setArtistId("5");
        albumInfoMock5.setArtistName("Funkadelic");
        albumInfoMock5.setGenre("Funk");
        albumInfoMock5.setYear(new BigDecimal("1990"));
        albumInfoMock5.setAverageRating(new BigDecimal("2"));
        albumInfoMock5.setPersonalRating(new BigDecimal("4"));
        albumInfoMock5.setImageLink("https://upload.wikimedia.org/wikipedia/en/a/ad/UncleJamWantsYou.jpg");
        List<String> album5songsList = new ArrayList<String>();
        album5songsList.add("5");
        albumInfoMock5.setSongsList(album5songsList);
        albums.add(albumInfoMock5);

        AlbumInfo albumInfoMock6 = new AlbumInfo();
        albumInfoMock6.setAlbumId("6");
        albumInfoMock6.setAlbumName("Rainbow Connection");
        albumInfoMock6.setArtistId("6");
        albumInfoMock6.setArtistName("Willie Nelson");
        albumInfoMock6.setGenre("Country");
        albumInfoMock6.setYear(new BigDecimal("1970"));
        albumInfoMock6.setAverageRating(new BigDecimal("1"));
        albumInfoMock6.setPersonalRating(new BigDecimal("3"));
        albumInfoMock6.setImageLink("https://upload.wikimedia.org/wikipedia/en/7/7c/Willie-Nelson-Rainbow-Connection.jpg");
        List<String> album6songsList = new ArrayList<String>();
        album6songsList.add("6");
        albumInfoMock6.setSongsList(album6songsList);
        albums.add(albumInfoMock6);

        AlbumInfo albumInfoMock7 = new AlbumInfo();
        albumInfoMock7.setAlbumId("7");
        albumInfoMock7.setAlbumName("Surfing with the Alien");
        albumInfoMock7.setArtistId("7");
        albumInfoMock7.setArtistName("Joe Satriani");
        albumInfoMock7.setGenre("Rock");
        albumInfoMock7.setYear(new BigDecimal("1990"));
        albumInfoMock7.setAverageRating(new BigDecimal("4"));
        albumInfoMock7.setPersonalRating(new BigDecimal("3"));
        albumInfoMock7.setImageLink("http://www.satriani.com/discography/Surfing_With_The_Alien/Surfing_With_The_Alien.jpg");
        List<String> album7songsList = new ArrayList<String>();
        album7songsList.add("7");
        albumInfoMock7.setSongsList(album7songsList);
        albums.add(albumInfoMock7);

        AlbumInfo albumInfoMock8 = new AlbumInfo();
        albumInfoMock8.setAlbumId("8");
        albumInfoMock8.setAlbumName("Is There Love in Space?");
        albumInfoMock8.setArtistId("8");
        albumInfoMock8.setArtistName("Joe Satriani");
        albumInfoMock8.setGenre("Rock");
        albumInfoMock8.setYear(new BigDecimal("1988"));
        albumInfoMock8.setAverageRating(new BigDecimal("4"));
        albumInfoMock8.setPersonalRating(new BigDecimal("4"));
        albumInfoMock8.setImageLink("https://upload.wikimedia.org/wikipedia/en/5/50/Joespace.jpg");
        List<String> album8songsList = new ArrayList<String>();
        album8songsList.add("8");
        albumInfoMock8.setSongsList(album8songsList);
        albums.add(albumInfoMock8);
        
        AlbumInfo albumInfoMock9 = new AlbumInfo();
        albumInfoMock9.setAlbumId("9");
        albumInfoMock9.setAlbumName("That's the Way of the World");
        albumInfoMock9.setArtistId("9");
        albumInfoMock9.setArtistName("Earth wind and fire");
        albumInfoMock9.setGenre("Funk");
        albumInfoMock9.setYear(new BigDecimal("1960"));
        albumInfoMock9.setAverageRating(new BigDecimal("2"));
        albumInfoMock9.setPersonalRating(new BigDecimal("2"));
        albumInfoMock9.setImageLink("https://upload.wikimedia.org/wikipedia/en/0/03/Whiskeytown-Stranger%27s_Almanac_%28album_cover%29.jpg");
        List<String> album9songsList = new ArrayList<String>();
        album9songsList.add("9");
        albumInfoMock9.setSongsList(album9songsList);
        albums.add(albumInfoMock9);

        albumList.setAlbums(albums);
    }
}
