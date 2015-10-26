package io.swagger.mock;

import io.swagger.model.ArtistList;
import io.swagger.model.ArtistInfo;

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
        artists.add(artistMockData1);
        
        List<String> artistMockAlbums1 = new ArrayList<String>();
        artistMockAlbums1.add("1");
        artistMockData1.setAlbumsList(artistMockAlbums1);
        
        List<String> artistMockSongs1 = new ArrayList<String>();
        artistMockSongs1.add("1");
        artistMockData1.setSongsList(artistMockSongs1);

        ArtistInfo artistMockData2 = new ArtistInfo();
        artistMockData2.setArtistId("2");
        artistMockData2.setArtistName("Pink Floyd");
        artistMockData2.setImageLink("http://www.billboard.com/files/styles/promo_650/public/media/pink-floyd-1973-billboard-650.jpg");
        artists.add(artistMockData2);
        
        List<String> artistMockAlbums2 = new ArrayList<String>();
        artistMockAlbums2.add("2");
        artistMockData2.setAlbumsList(artistMockAlbums2);
        
        List<String> artistMockSongs2 = new ArrayList<String>();
        artistMockSongs2.add("2");
        artistMockData2.setSongsList(artistMockSongs2);

        ArtistInfo artistMockData3 = new ArtistInfo();
        artistMockData3.setArtistId("3");
        artistMockData3.setArtistName("Janis Joplin");
        artistMockData3.setImageLink("http://www.thisdayinmusic.com/upload/janis_joplin_6727.jpg");
        artists.add(artistMockData3);
        
        List<String> artistMockAlbums3 = new ArrayList<String>();
        artistMockAlbums3.add("3");
        artistMockData3.setAlbumsList(artistMockAlbums3);

        List<String> artistMockSongs3 = new ArrayList<String>();
        artistMockSongs3.add("3");
        artistMockData3.setSongsList(artistMockSongs3);

        ArtistInfo artistMockData4 = new ArtistInfo();
        artistMockData4.setArtistId("4");
        artistMockData4.setArtistName("Led Zeppelin");
        artistMockData4.setImageLink("http://d2x3wmakafwqf5.cloudfront.net/wordpress/wp-content/blogs.dir/106/files/2014/06/led_zeppelin_wallpaper_blac_and_white.jpg");
        artists.add(artistMockData4);
        
        List<String> artistMockAlbums4 = new ArrayList<String>();
        artistMockAlbums4.add("4");
        artistMockData4.setAlbumsList(artistMockAlbums4);

        List<String> artistMockSongs4 = new ArrayList<String>();
        artistMockSongs4.add("4");
        artistMockData4.setSongsList(artistMockSongs4);
        
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
}
