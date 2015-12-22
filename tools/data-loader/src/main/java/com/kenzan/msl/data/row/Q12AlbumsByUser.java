package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;
import com.kenzan.msl.data.NormalizedRow;

public class Q12AlbumsByUser {

    private final UUID userId;
    private final ContentType contentType = ContentType.ALBUM;
    private final Date favoritesTimestamp;
    private final UUID albumId;
    private final String albumName;
    private final int albumYear;
    private final UUID aritstId;
    private final UUID artistMbid;
    private final String artistName;
    private final String imageLink;
    
    public Q12AlbumsByUser(final NormalizedRow normalizedRow, final UUID userId,  final Date favoritesTimestamp) {

        this.userId = userId;
        this.favoritesTimestamp = favoritesTimestamp;
        this.albumId = normalizedRow.getAlbum().getId();
        this.albumName = normalizedRow.getAlbum().getName();
        this.albumYear = normalizedRow.getAlbum().getYear();
        this.aritstId = normalizedRow.getArtist().getId();
        this.artistMbid = normalizedRow.getArtist().getMbid();
        this.artistName = normalizedRow.getArtist().getName();
        this.imageLink = normalizedRow.getAlbum().getImageLink();
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(userId.toString());
        row.add(contentType.toString());
        row.add(RowUtil.formatTimestamp(favoritesTimestamp));
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
