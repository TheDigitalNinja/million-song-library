package io.swagger.mock;

import io.swagger.model.SongInfo;
import io.swagger.model.SongList;

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
        songMockData3.setGenre("Rock and roll");
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
        songMockData4.setGenre("Rock and roll");
        songs.add(songMockData4);

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

    public SongList browseSongs(String pagingState, Integer items, String facets, String sortFields) {
        List<SongInfo> browsedSongs = songList.getSongs();

        if (pagingState != null && !pagingState.isEmpty()) {
            // TODO implement pagination
            System.out.println("Pagination: " + pagingState);
        }
        if (facets != null && facets.length() > 0) {
            // TODO implement facet filtering
            System.out.println("Filtering by facet: " + facets);
        }

        // TODO if no items are provided should return 25 results
        if (items != null && items > 0) {
            browsedSongs = new ArrayList<SongInfo>();

            for (int i = 0; i < items && i < songList.getSongs().size(); i++) {
                browsedSongs.add(songList.getSongs().get(i));
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
