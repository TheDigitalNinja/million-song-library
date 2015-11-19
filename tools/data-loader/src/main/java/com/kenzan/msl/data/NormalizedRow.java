package com.kenzan.msl.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.kenzan.msl.data.row.RowUtil;

/**
 * {@link NormalizedRow} contains "normalized" content objects with appropriate
 * data types
 *
 * @author peterburt
 */
public class NormalizedRow {

    private static final String FIELD_DELIMITER = ",";

    private final NormalizedArtist artist;
    private final NormalizedAlbum album;
    private final NormalizedSong song;

    private NormalizedRow(NormalizedRowBuilder builder) {

        this.artist = builder.artist;
        this.album = builder.album;
        this.song = builder.song;
    }

    public NormalizedArtist getArtist() {
        return artist;
    }

    public NormalizedAlbum getAlbum() {
        return album;
    }

    public NormalizedSong getSong() {
        return song;
    }

    public String toString() {

        final String[] csv = new String[Field.values().length];
        csv[Field.ARTIST_ID.getIndex()] = artist.getId().toString();
        csv[Field.ARTIST_MBID.getIndex()] = artist.getMbid().toString();
        csv[Field.ARTIST_NAME.getIndex()] = artist.getName();
        csv[Field.ARTIST_HOTNESS.getIndex()] = RowUtil.formatHotness(artist.getHotness());
        final List<String> genres = new ArrayList<String>();
        if (!artist.getGenres().isEmpty()) {
            for (final Genre genre : artist.getGenres()) {
                genres.add(genre.toString());
            }
        }
        csv[Field.ARTIST_GENRES.getIndex()] = String.join("|", genres);
        csv[Field.ARTIST_SIMILAR_ARTISTS.getIndex()] = String.join("|", artist.getSimilarArtists());
        csv[Field.ALBUM_ID.getIndex()] = album.getId().toString();
        csv[Field.ALBUM_NAME.getIndex()] = album.getName();
        csv[Field.ALBUM_YEAR.getIndex()] = RowUtil.formatYear(album.getYear());
        csv[Field.ALBUM_HOTNESS.getIndex()] = RowUtil.formatHotness(album.getHotness());
        csv[Field.SONG_ID.getIndex()] = song.getId().toString();
        csv[Field.SONG_NAME.getIndex()] = song.getName();
        csv[Field.SONG_DURATION.getIndex()] = Integer.toString(song.getDuration());
        csv[Field.SONG_HOTNESS.getIndex()] = RowUtil.formatHotness(song.getHotness());
        return String.join(FIELD_DELIMITER, csv);
    }

    public static class NormalizedRowBuilder {

        private final NormalizedArtist artist;
        private final NormalizedAlbum album;
        private final NormalizedSong song;

        public NormalizedRowBuilder(final String csvRow) {

            final String[] parts = csvRow.split(FIELD_DELIMITER, -1);

            final ArrayList<Genre> genres = new ArrayList<Genre>();
            if (StringUtils.isNotBlank(parts[Field.ARTIST_GENRES.getIndex()])) {
                final String[] rawGenres = parts[Field.ARTIST_GENRES.getIndex()].split("\\|");
                for (final String rawGenre : rawGenres) {
                    for (final Genre genre : Genre.values()) {
                        if (WordUtils.capitalize(rawGenre).equals(genre.toString())) {
                            genres.add(genre);
                        }
                    }
                }
            }
            final ArrayList<String> similarArtists = new ArrayList<String>();
            if (StringUtils.isNotBlank(parts[Field.ARTIST_SIMILAR_ARTISTS.getIndex()])) {
                final String[] rawSimilarArtists = parts[Field.ARTIST_SIMILAR_ARTISTS.getIndex()].split("\\|");
                for (final String rawSimilarArtist : rawSimilarArtists) {
                    similarArtists.add(rawSimilarArtist);
                }
            }

            this.artist = new NormalizedArtist.ArtistBuilder(UUID.fromString(parts[Field.ARTIST_ID.getIndex()]),
                    UUID.fromString(parts[Field.ARTIST_MBID.getIndex()]), parts[Field.ARTIST_NAME.getIndex()],
                    Float.parseFloat(parts[Field.ARTIST_HOTNESS.getIndex()]), genres, similarArtists).build();
            this.album = new NormalizedAlbum.AlbumBuilder(UUID.fromString(parts[Field.ALBUM_ID.getIndex()]),
                    parts[Field.ALBUM_NAME.getIndex()], Integer.parseInt(parts[Field.ALBUM_YEAR.getIndex()]),
                    Float.parseFloat(parts[Field.ALBUM_HOTNESS.getIndex()])).build();
            this.song = new NormalizedSong.SongBuilder(UUID.fromString(parts[Field.SONG_ID.getIndex()]),
                    parts[Field.SONG_NAME.getIndex()], Integer.parseInt(parts[Field.SONG_DURATION.getIndex()]),
                    Float.parseFloat(parts[Field.SONG_HOTNESS.getIndex()])).build();
        }

        public NormalizedRow build() {

            return new NormalizedRow(this);
        }
    }
}
