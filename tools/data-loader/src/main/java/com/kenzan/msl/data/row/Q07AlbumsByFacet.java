package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;
import com.kenzan.msl.data.NormalizedRow;

public class Q07AlbumsByFacet {

    private final String facetName;
    private final ContentType contentType = ContentType.ALBUM;
    private final String albumName;
    private final UUID albumId;
    private final int albumYear;
    private final String imageLink;
    private final UUID aritstId;
    private final UUID artistMbid;
    private final String artistName;
    
    public Q07AlbumsByFacet(final NormalizedRow normalizedRow, final String facetName, String url) {

        this.facetName = facetName;
        this.albumName = normalizedRow.getAlbum().getName();
        this.albumId = normalizedRow.getAlbum().getId();
        this.albumYear = normalizedRow.getAlbum().getYear();
        this.imageLink = url;
        this.aritstId = normalizedRow.getArtist().getId();
        this.artistMbid = normalizedRow.getArtist().getMbid();
        this.artistName = normalizedRow.getArtist().getName();
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(facetName);
        row.add(contentType.toString());
        row.add(RowUtil.formatText(albumName));
        row.add(albumId.toString());
        row.add(RowUtil.formatInt(albumYear));
        row.add(imageLink);
        row.add(aritstId.toString());
        row.add(artistMbid.toString());
        row.add(RowUtil.formatText(artistName));
        return String.join(RowUtil.FIELD_DELIMITER, row);
    }
}
