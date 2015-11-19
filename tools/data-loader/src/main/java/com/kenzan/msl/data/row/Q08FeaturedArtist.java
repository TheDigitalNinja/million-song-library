package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;
import com.kenzan.msl.data.NormalizedRow;

public class Q08FeaturedArtist {

    private final String hotnessBucket = "Hotness01";
    private final ContentType contentType = ContentType.ARTIST;
    private final float artistHotness;
    private final UUID aritstId;
    private final UUID artistMbid;
    private final String artistName;
    
    public Q08FeaturedArtist(final NormalizedRow normalizedRow) {
        
        this.artistHotness = normalizedRow.getAlbum().getHotness();
        this.aritstId = normalizedRow.getArtist().getId();
        this.artistMbid = normalizedRow.getArtist().getMbid();
        this.artistName = normalizedRow.getArtist().getName();
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(hotnessBucket);
        row.add(contentType.toString());
        row.add(RowUtil.formatHotness(artistHotness));
        row.add(aritstId.toString());
        row.add(artistMbid.toString());
        row.add(RowUtil.formatText(artistName));
        return String.join(RowUtil.FIELD_DELIMITER, row);
    }
}
