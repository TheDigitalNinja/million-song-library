package io.swagger.mock;

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
        albumInfoMock1.setAverageRating(new BigDecimal("1115.37"));
        albumInfoMock1.setPersonalRating(new BigDecimal("1115.37"));
        albumInfoMock1.setImageLink("https://upload.wikimedia.org/wikipedia/en/6/6d/SteveVaiAlienLoveSecrets.jpg");
        albums.add(albumInfoMock1);

        AlbumInfo albumInfoMock2 = new AlbumInfo();
        albumInfoMock2.setAlbumId("1");
        albumInfoMock2.setAlbumName("The Wall");
        albumInfoMock2.setArtistId("2");
        albumInfoMock2.setArtistName("Pink Floyd");
        albumInfoMock2.setGenre("Rock");
        albumInfoMock2.setYear(new BigDecimal("1115.37"));
        albumInfoMock2.setAverageRating(new BigDecimal("1115.37"));
        albumInfoMock2.setPersonalRating(new BigDecimal("1115.37"));
        albumInfoMock2.setImageLink("http://wordsushi.com/wp-content/uploads/2012/11/The+Wall++high+resolution+png.png");
        albums.add(albumInfoMock2);

        AlbumInfo albumInfoMock3 = new AlbumInfo();
        albumInfoMock3.setAlbumId("3");
        albumInfoMock3.setAlbumName("Farewell song");
        albumInfoMock3.setArtistId("3");
        albumInfoMock3.setArtistName("Janis Joplin");
        albumInfoMock3.setGenre("Rock and Roll");
        albumInfoMock3.setYear(new BigDecimal("1115.37"));
        albumInfoMock3.setAverageRating(new BigDecimal("1115.37"));
        albumInfoMock3.setPersonalRating(new BigDecimal("1115.37"));
        albumInfoMock3.setImageLink("https://upload.wikimedia.org/wikipedia/en/0/0f/FSJanis.jpg");
        albums.add(albumInfoMock3);

        AlbumInfo albumInfoMock4 = new AlbumInfo();
        albumInfoMock4.setAlbumId("4");
        albumInfoMock4.setAlbumName("Led Zeppelin III");
        albumInfoMock4.setArtistId("4");
        albumInfoMock4.setArtistName("Led Zeppelin");
        albumInfoMock4.setGenre("Rock");
        albumInfoMock4.setYear(new BigDecimal("1115.37"));
        albumInfoMock4.setAverageRating(new BigDecimal("1115.37"));
        albumInfoMock4.setPersonalRating(new BigDecimal("1115.37"));
        albumInfoMock4.setImageLink("https://upload.wikimedia.org/wikipedia/en/5/5f/Led_Zeppelin_-_Led_Zeppelin_III.png");
        albums.add(albumInfoMock4);

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
}
