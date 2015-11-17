package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;
import com.kenzan.msl.data.NormalizedRow;

public class Q04FeaturedSong {

    private final String hotnessBucket = "Hotness01";
    private final ContentType contentType = ContentType.SONG;
    private final float songHotness;
    private final UUID songId;
    private final UUID albumId;
    private final String albumName;
    private final int albumYear;
    private final UUID aritstId;
    private final UUID artistMbid;
    private final String artistName;
    private final int songDuration;
    private final String songName;
    
    public Q04FeaturedSong(final NormalizedRow normalizedRow) {
        
        this.songHotness = normalizedRow.getSong().getHotness();
        this.songId = normalizedRow.getSong().getId();
        this.albumId = normalizedRow.getAlbum().getId();
        this.albumName = normalizedRow.getAlbum().getName();
        this.albumYear = normalizedRow.getAlbum().getYear();
        this.aritstId = normalizedRow.getArtist().getId();
        this.artistMbid = normalizedRow.getArtist().getMbid();
        this.artistName = normalizedRow.getArtist().getName();
        this.songDuration = normalizedRow.getSong().getDuration();
        this.songName = normalizedRow.getSong().getName();
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(hotnessBucket);
        row.add(contentType.toString());
        row.add(RowUtil.formatHotness(songHotness));
        row.add(songId.toString());
        row.add(albumId.toString());
        row.add(RowUtil.formatText(albumName));
        row.add(RowUtil.formatYear(albumYear));
        row.add(aritstId.toString());
        row.add(artistMbid.toString());
        row.add(RowUtil.formatText(artistName));
        row.add(Integer.toString(songDuration));
        row.add(RowUtil.formatText(songName));
        return String.join(",", row);
    }
}
