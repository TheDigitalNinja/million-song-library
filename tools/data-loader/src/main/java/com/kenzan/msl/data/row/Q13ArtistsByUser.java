package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;
import com.kenzan.msl.data.NormalizedRow;

public class Q13ArtistsByUser {

    private final UUID userId;
    private final ContentType contentType = ContentType.ARTIST;
    private final Date favoritesTimestamp;
    private final UUID aritstId;
    private final UUID artistMbid;
    private final String artistName;
    
    public Q13ArtistsByUser(final NormalizedRow normalizedRow, final UUID userId,  final Date favoritesTimestamp) {

        this.userId = userId;
        this.favoritesTimestamp = favoritesTimestamp;
        this.aritstId = normalizedRow.getArtist().getId();
        this.artistMbid = normalizedRow.getArtist().getMbid();
        this.artistName = normalizedRow.getArtist().getName();
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(userId.toString());
        row.add(contentType.toString());
        row.add(RowUtil.formatTimestamp(favoritesTimestamp));
        row.add(aritstId.toString());
        row.add(artistMbid.toString());
        row.add(RowUtil.formatText(artistName));
        return String.join(RowUtil.FIELD_DELIMITER, row);
    }
}
