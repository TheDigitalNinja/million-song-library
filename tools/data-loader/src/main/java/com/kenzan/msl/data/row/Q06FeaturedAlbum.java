package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;
import com.kenzan.msl.data.NormalizedRow;

public class Q06FeaturedAlbum {

    private final String hotnessBucket = "Hotness01";
    private final ContentType contentType = ContentType.ALBUM;
    private final float albumHotness;
    private final UUID albumId;
    private final String albumName;
    private final int albumYear;
    private final UUID aritstId;
    private final UUID artistMbid;
    private final String artistName;
    private final String imageLink;
    
    public Q06FeaturedAlbum(final NormalizedRow normalizedRow, final String imageLink) {
        
        this.albumHotness = normalizedRow.getAlbum().getHotness();
        this.albumId = normalizedRow.getAlbum().getId();
        this.albumName = normalizedRow.getAlbum().getName();
        this.albumYear = normalizedRow.getAlbum().getYear();
        this.aritstId = normalizedRow.getArtist().getId();
        this.artistMbid = normalizedRow.getArtist().getMbid();
        this.artistName = normalizedRow.getArtist().getName();
        this.imageLink = imageLink;
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(hotnessBucket);
        row.add(contentType.toString());
        row.add(RowUtil.formatHotness(albumHotness));
        row.add(albumId.toString());
        row.add(RowUtil.formatText(albumName));
        row.add(RowUtil.formatInt(albumYear));
        row.add(aritstId.toString());
        row.add(artistMbid.toString());
        row.add(RowUtil.formatText(artistName));
        row.add(RowUtil.formatText(imageLink));
        return String.join(RowUtil.FIELD_DELIMITER, row);
    }
}
