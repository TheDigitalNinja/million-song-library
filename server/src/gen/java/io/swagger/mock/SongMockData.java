package io.swagger.mock;

import io.swagger.api.factories.FacetServiceFactory;
import io.swagger.model.AlbumInfo;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anram88 on 10/21/15.
 */
public class SongMockData {

    public SongList songList;

    public SongMockData() {

        this.songList = new SongList();
        List<SongInfo> songs = new ArrayList<SongInfo>();

        SongInfo songMockData1 = new SongInfo();
        songMockData1.setSongId("1");
        songMockData1.setSongName("Tender Surrender");
        songMockData1.setImageLink("https://upload.wikimedia.org/wikipedia/en/6/6d/SteveVaiAlienLoveSecrets.jpg");
        songMockData1.setArtistId("1");
        songMockData1.setArtistName("Vai, Steve");
        songMockData1.setAlbumId("1");
        songMockData1.setAlbumName("Alien Love Secrets");
        songMockData1.setDuration(230);
        songMockData1.setGenre("Rock");
        songMockData1.setAverageRating(new BigDecimal("4.5"));
        songs.add(songMockData1);

        SongInfo songMockData2 = new SongInfo();
        songMockData2.setSongId("2");
        songMockData2.setSongName("Hey You!");
        songMockData2.setImageLink("http://wordsushi.com/wp-content/uploads/2012/11/The+Wall++high+resolution+png.png");
        songMockData2.setArtistId("2");
        songMockData2.setArtistName("Pink Floyd");
        songMockData2.setAlbumId("2");
        songMockData2.setAlbumName("The Wall");
        songMockData2.setDuration(230);
        songMockData2.setGenre("Rock");
        songMockData2.setAverageRating(new BigDecimal("3.5"));
        songs.add(songMockData2);

        SongInfo songMockData3 = new SongInfo();
        songMockData3.setSongId("3");
        songMockData3.setSongName("Farewell Song");
        songMockData3.setImageLink("https://upload.wikimedia.org/wikipedia/en/0/0f/FSJanis.jpg");
        songMockData3.setArtistId("3");
        songMockData3.setArtistName("Janis Joplin");
        songMockData3.setAlbumId("3");
        songMockData3.setAlbumName("Farewell song");
        songMockData3.setDuration(230);
        songMockData3.setGenre("Rock and Roll");
        songMockData3.setAverageRating(new BigDecimal("3.7"));
        songs.add(songMockData3);

        SongInfo songMockData4 = new SongInfo();
        songMockData4.setSongId("4");
        songMockData4.setSongName("Since I've Been Loving You");
        songMockData4.setImageLink("https://upload.wikimedia.org/wikipedia/en/5/5f/Led_Zeppelin_-_Led_Zeppelin_III.png");
        songMockData4.setArtistId("4");
        songMockData4.setArtistName("Led Zeppelin");
        songMockData4.setAlbumId("4");
        songMockData4.setAlbumName("Led Zeppelin III");
        songMockData4.setDuration(230);
        songMockData4.setGenre("Rock and Roll");
        songMockData4.setAverageRating(new BigDecimal("3.5"));
        songs.add(songMockData4);

        SongInfo songMockData5 = new SongInfo();
        songMockData5.setSongId("5");
        songMockData5.setSongName("Field Maneuvers");
        songMockData5.setImageLink("https://upload.wikimedia.org/wikipedia/en/a/ad/UncleJamWantsYou.jpg");
        songMockData5.setArtistId("5");
        songMockData5.setArtistName("Funkadelic");
        songMockData5.setAlbumId("5");
        songMockData5.setAlbumName("Uncle Jam Wants You");
        songMockData5.setDuration(230);
        songMockData5.setGenre("Funk");
        songMockData5.setAverageRating(new BigDecimal("2.5"));
        songs.add(songMockData5);

        SongInfo songMockData6 = new SongInfo();
        songMockData6.setSongId("6");
        songMockData6.setSongName("Rainbow Connection");
        songMockData6.setImageLink("https://upload.wikimedia.org/wikipedia/en/7/7c/Willie-Nelson-Rainbow-Connection.jpg");
        songMockData6.setArtistId("6");
        songMockData6.setArtistName("Willie Nelson");
        songMockData6.setAlbumId("6");
        songMockData6.setAlbumName("Rainbow Connection");
        songMockData6.setDuration(230);
        songMockData6.setAverageRating(new BigDecimal("1.5"));
        songMockData6.setGenre("Country");
        songs.add(songMockData6);

        songList.setSongs(songs);
    }

    public SongInfo getSong(String songId) {
        for (SongInfo song : songList.getSongs()) {
            if (songId.equals(song.getSongId())) {
                return song;
            }
        }
        return new SongInfo();
    }

    public SongList browseSongs(String pagingState, Integer items, String facetList, String sortFields) {
        List<SongInfo> browsedSongs = songList.getSongs();

        if (pagingState != null && !pagingState.isEmpty()) {
            // TODO implement pagination
            System.out.println("Pagination: " + pagingState);
        }

        if (facetList != null && facetList.length() > 0) {

            //TODO replace reference to mock data
            FacetMockData facetMockData = new FacetMockData();

            System.out.println("Filtering by facet(s): " + facetList);

            List<SongInfo> pivotSongs = browsedSongs;
            browsedSongs = new ArrayList<SongInfo>();

            String[] facets = facetList.split(",");
            for (String facet : facets) {
                //TODO replace reference to mock data
                if (FacetServiceFactory.isRatingFacet(facet, facetMockData.mockFacets)) {
                    browsedSongs = FacetServiceFactory.filterSongsByRatingFacet(pivotSongs, facet);
                } else {
                    for (SongInfo song : pivotSongs) {
                        if (FacetServiceFactory.getFacet(facet, facetMockData.mockFacets) != null) {
                            String facetName = FacetServiceFactory.getFacet(facet, facetMockData.mockFacets).getName();
                            if (song.getGenre().equals(facetName)) {
                                browsedSongs.add(song);
                            }
                        }
                    }
                }
            }
        }

        // TODO if no items are provided should return 25 results
        if (items != null && items > 0) {
            List<SongInfo> pivotSongs = browsedSongs;
            browsedSongs = new ArrayList<SongInfo>();
            for (int i = 0; i < items && i < pivotSongs.size(); i++) {
                browsedSongs.add(pivotSongs.get(i));
            }
        }

        if (sortFields != null && !sortFields.isEmpty()) {
            //TODO implement sorting by sortFields
            System.out.println("Sorting results by: " + sortFields);
        }

        SongList results = new SongList();
        results.setSongs(browsedSongs);

        return results;
    }

}
