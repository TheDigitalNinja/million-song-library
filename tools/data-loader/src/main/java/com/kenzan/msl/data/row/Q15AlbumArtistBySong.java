package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.kenzan.msl.data.Genre;
import com.kenzan.msl.data.NormalizedRow;

public class Q15AlbumArtistBySong {

    private final UUID songId;
    private final UUID albumId;
    private final String albumName;
    private final int albumYear;
    private final List<Genre> artistGenres;
    private final UUID aritstId;
    private final UUID artistMbid;
    private final String artistName;
    private final Map<UUID, String> similarArtists;
    private final int songDuration;
    private final String songName;
    private final String imageLink;
    
    public Q15AlbumArtistBySong(final NormalizedRow normalizedRow) {

        this.songId = normalizedRow.getSong().getId();
        this.albumId = normalizedRow.getAlbum().getId();
        this.albumName = normalizedRow.getAlbum().getName();
        this.albumYear = normalizedRow.getAlbum().getYear();
        this.artistGenres = normalizedRow.getArtist().getGenres();
        this.aritstId = normalizedRow.getArtist().getId();
        this.artistMbid = normalizedRow.getArtist().getMbid();
        this.artistName = normalizedRow.getArtist().getName();
        final Map<UUID, String> artistMap = new HashMap<UUID, String>();
        for (final String artistStr : normalizedRow.getArtist().getSimilarArtists()) {
            final String[] parts = artistStr.split(": ");
            artistMap.put(UUID.fromString(parts[0]), parts[1]);
        }
        this.similarArtists = artistMap;
        this.songDuration = normalizedRow.getSong().getDuration();
        this.songName = normalizedRow.getSong().getName();
        this.imageLink = normalizedRow.getAlbum().getImageLink();
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(songId.toString());
        row.add(albumId.toString());
        row.add(RowUtil.formatText(albumName));
        row.add(RowUtil.formatInt(albumYear));
        row.add(RowUtil.formatGenres(artistGenres));
        row.add(aritstId.toString());
        row.add(artistMbid.toString());
        row.add(RowUtil.formatText(artistName));
        row.add(RowUtil.formatSimilarArtists(similarArtists));
        row.add(RowUtil.formatInt(songDuration));
        row.add(RowUtil.formatText(songName));
        row.add(RowUtil.formatText(imageLink));
        return String.join(RowUtil.FIELD_DELIMITER, row);
    }
}
