package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.kenzan.msl.data.Genre;
import com.kenzan.msl.data.NormalizedRow;

public class Q14SongsArtistByAlbum {

    private final UUID albumId;
    private final String songName;
    private final UUID songId;
    private final String albumName;
    private final int albumYear;
    private final List<Genre> artistGenres;
    private final UUID aritstId;
    private final UUID artistMbid;
    private final String artistName;
    private final Map<UUID, String> similarArtists;
    private final int songDuration;
    
    public Q14SongsArtistByAlbum(final NormalizedRow normalizedRow) {

        this.albumId = normalizedRow.getAlbum().getId();
        this.songName = normalizedRow.getSong().getName();
        this.songId = normalizedRow.getSong().getId();
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
    }
    
    public String toString() {
        
        final List<String> row = new ArrayList<String>();
        row.add(albumId.toString());
        row.add(RowUtil.formatText(songName));
        row.add(songId.toString());
        row.add(RowUtil.formatText(albumName));
        row.add(RowUtil.formatYear(albumYear));
        final List<String> genres = new ArrayList<String>();
        for (final Genre genre : artistGenres) {
            genres.add(RowUtil.formatTextInCollection(genre.toString()));
        }
        row.add(String.format("\"{%s}\"", String.join(", ", genres)));
        row.add(aritstId.toString());
        row.add(artistMbid.toString());
        row.add(RowUtil.formatText(artistName));
        final List<String> similars = new ArrayList<String>();
        for (final UUID id : similarArtists.keySet()) {
            similars.add(id.toString() + ": " + RowUtil.formatTextInCollection(similarArtists.get(id)));
        }
        row.add(String.format("\"{%s}\"", String.join(", ", similars)));
        row.add(Integer.toString(songDuration));
        return String.join(",", row);
    }
}
