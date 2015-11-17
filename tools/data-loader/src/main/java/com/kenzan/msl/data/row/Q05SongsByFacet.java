package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;
import com.kenzan.msl.data.NormalizedRow;

public class Q05SongsByFacet {

    private final String facetName;
    private final ContentType contentType = ContentType.SONG;
    private final String song_name;
    private final UUID song_id;
    private final UUID album_id;
    private final String album_name;
    private final int album_year;
    private final UUID aritst_id;
    private final UUID artist_mbid;
    private final String artist_name;
    private final int song_duration;
    
    public Q05SongsByFacet(final NormalizedRow normalizedRow, final String facetName) {

        this.facetName = facetName;
        this.song_name = normalizedRow.getSong().getName();
        this.song_id = normalizedRow.getSong().getId();
        this.album_id = normalizedRow.getAlbum().getId();
        this.album_name = normalizedRow.getAlbum().getName();
        this.album_year = normalizedRow.getAlbum().getYear();
        this.aritst_id = normalizedRow.getArtist().getId();
        this.artist_mbid = normalizedRow.getArtist().getMbid();
        this.artist_name = normalizedRow.getArtist().getName();
        this.song_duration = normalizedRow.getSong().getDuration();
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(facetName);
        row.add(contentType.toString());
        row.add(song_name);
        row.add(song_id.toString());
        row.add(album_id.toString());
        row.add(album_name);
        row.add(Integer.toString(album_year));
        row.add(aritst_id.toString());
        row.add(artist_mbid.toString());
        row.add(artist_name);
        row.add(Integer.toString(song_duration));
        return String.join(",", row);
    }
}
