package com.kenzan.msl.server.mock;

import io.swagger.api.factories.FacetServiceFactory;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;

import java.util.ArrayList;
import java.util.List;

public class SongMockData {

    public SongList songList;
    private FacetMockData facetMockData = new FacetMockData();

    public SongInfo getSong(String songId) {
        for ( SongInfo song : songList.getSongs() ) {
            if ( songId.equals(song.getSongId()) ) {
                return song;
            }
        }
        return new SongInfo();
    }

    private List<SongInfo> applyGenreFacet(String[] facets, List<SongInfo> songList) {
        List<SongInfo> result = new ArrayList<>();
        boolean hasRatingFacet = false;
        for ( String facet : facets ) {
            if ( !FacetServiceFactory.isRatingFacet(facet, facetMockData.getRatingFacets()) ) {
                hasRatingFacet = true;
                for ( SongInfo artist : songList ) {
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

        return songList;
    }

    private List<SongInfo> applyRatingFacet(String[] facets, List<SongInfo> songList) {
        for ( String facet : facets ) {
            if ( FacetServiceFactory.isRatingFacet(facet, facetMockData.getRatingFacets()) ) {
                return FacetServiceFactory.filterSongsByRatingFacet(songList, facet);
            }
        }
        return songList;
    }

    public SongList browseSongs(String pagingState, Integer items, String facetList) {
        List<SongInfo> browsedSongs = songList.getSongs();

        if ( pagingState != null && !pagingState.isEmpty() ) {
            // TODO implement pagination
            System.out.println("Pagination: " + pagingState);
        }

        if ( facetList != null && facetList.length() > 0 ) {
            // TODO replace reference to mock data
            System.out.println("Filtering by facet(s): " + facetList);
            String[] facets = facetList.split(",");
            browsedSongs = applyRatingFacet(facets, browsedSongs);
            browsedSongs = applyGenreFacet(facets, browsedSongs);
        }

        // TODO if no items are provided should return 25 results
        if ( items != null && items > 0 ) {
            List<SongInfo> pivotSongs = browsedSongs;
            browsedSongs = new ArrayList<>();
            for ( int i = 0; i < items && i < pivotSongs.size(); i++ ) {
                browsedSongs.add(pivotSongs.get(i));
            }
        }

        SongList results = new SongList();
        results.setSongs(browsedSongs);

        return results;
    }

    public SongMockData() {

        this.songList = new SongList();
        List<SongInfo> songs = new ArrayList<>();

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
        songMockData1.setAverageRating(4);
        songMockData1.setYear(2009);
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
        songMockData2.setAverageRating(4);
        songMockData2.setYear(1979);
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
        songMockData3.setAverageRating(3);
        songMockData3.setYear(1982);
        songs.add(songMockData3);

        SongInfo songMockData4 = new SongInfo();
        songMockData4.setSongId("4");
        songMockData4.setSongName("Since I've Been Loving You");
        songMockData4
            .setImageLink("https://upload.wikimedia.org/wikipedia/en/5/5f/Led_Zeppelin_-_Led_Zeppelin_III.png");
        songMockData4.setArtistId("4");
        songMockData4.setArtistName("Led Zeppelin");
        songMockData4.setAlbumId("4");
        songMockData4.setAlbumName("Led Zeppelin III");
        songMockData4.setDuration(230);
        songMockData4.setGenre("Rock and Roll");
        songMockData4.setAverageRating(4);
        songMockData4.setYear(1970);
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
        songMockData5.setAverageRating(2);
        songMockData5.setYear(1979);
        songs.add(songMockData5);

        SongInfo songMockData6 = new SongInfo();
        songMockData6.setSongId("6");
        songMockData6.setSongName("Rainbow Connection");
        songMockData6
            .setImageLink("https://upload.wikimedia.org/wikipedia/en/7/7c/Willie-Nelson-Rainbow-Connection.jpg");
        songMockData6.setArtistId("6");
        songMockData6.setArtistName("Willie Nelson");
        songMockData6.setAlbumId("6");
        songMockData6.setAlbumName("Rainbow Connection");
        songMockData6.setDuration(230);
        songMockData6.setGenre("Country");
        songMockData6.setAverageRating(1);
        songMockData6.setYear(2011);
        songs.add(songMockData6);

        SongInfo songMockData7 = new SongInfo();
        songMockData7.setSongId("7");
        songMockData7.setSongName("Surfing with the alien");
        songMockData7
            .setImageLink("http://www.satriani.com/discography/Surfing_With_The_Alien/Surfing_With_The_Alien.jpg");
        songMockData7.setArtistId("7");
        songMockData7.setArtistName("Joe Satriani");
        songMockData7.setAlbumId("7");
        songMockData7.setAlbumName("Surfing with the alien");
        songMockData7.setDuration(230);
        songMockData7.setGenre("Rock");
        songMockData7.setAverageRating(4);
        songMockData7.setYear(1987);
        songs.add(songMockData7);

        SongInfo songMockData8 = new SongInfo();
        songMockData8.setSongId("8");
        songMockData8.setSongName("Up In Flames");
        songMockData8.setImageLink("https://upload.wikimedia.org/wikipedia/en/5/50/Joespace.jpg");
        songMockData8.setArtistId("7");
        songMockData8.setArtistName("Joe Satriani");
        songMockData8.setAlbumId("8");
        songMockData8.setAlbumName("Is There Love in Space?");
        songMockData8.setDuration(230);
        songMockData8.setGenre("Rock");
        songMockData8.setAverageRating(4);
        songMockData8.setYear(2011);
        songs.add(songMockData8);

        SongInfo songMockData9 = new SongInfo();
        songMockData9.setSongId("9");
        songMockData9.setSongName("That's the Way of the World");
        songMockData9
            .setImageLink("https://upload.wikimedia.org/wikipedia/en/0/03/Whiskeytown-Stranger%27s_Almanac_%28album_cover%29.jpg");
        songMockData9.setArtistId("8");
        songMockData9.setArtistName("Earth wind and fire");
        songMockData9.setAlbumId("9");
        songMockData9.setAlbumName("That's the Way of the World");
        songMockData9.setDuration(230);
        songMockData9.setGenre("Funk");
        songMockData9.setAverageRating(2);
        songMockData9.setYear(1975);
        songs.add(songMockData9);

        songList.setSongs(songs);
    }

}
