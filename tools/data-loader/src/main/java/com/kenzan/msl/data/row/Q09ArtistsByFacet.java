package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;
import com.kenzan.msl.data.NormalizedRow;

public class Q09ArtistsByFacet {

    private final String facetName;
    private final ContentType contentType = ContentType.ARTIST;
    private final String artistName;
    private final UUID aritstId;
    private final UUID artistMbid;
    
    public Q09ArtistsByFacet(final NormalizedRow normalizedRow, final String facetName) {

        this.facetName = facetName;
        this.artistName = normalizedRow.getArtist().getName();
        this.aritstId = normalizedRow.getArtist().getId();
        this.artistMbid = normalizedRow.getArtist().getMbid();
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(facetName);
        row.add(contentType.toString());
        row.add(RowUtil.formatText(artistName));
        row.add(aritstId.toString());
        row.add(artistMbid.toString());
        return String.join(RowUtil.FIELD_DELIMITER, row);
    }
}
