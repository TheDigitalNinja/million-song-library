/**
 * 
 */
package com.kenzan.msl.data;

import java.util.Comparator;

/**
 * @author peterburt
 *
 */
public class RawRow {

    private static final String FIELD_DELIMITER = ",";

    private final RawArtist artist;
    private final RawAlbum album;
    private final RawSong song;

    private RawRow(RawRowBuilder builder) {

        this.artist = builder.artist;
        this.album = builder.album;
        this.song = builder.song;
    }

    public RawArtist getArtist() {
        return artist;
    }

    public RawAlbum getAlbum() {
        return album;
    }

    public RawSong getSong() {
        return song;
    }

    public String toString() {

        final String[] csv = new String[Field.values().length];
        csv[Field.ARTIST_ID.getIndex()] = artist.getId();
        csv[Field.ARTIST_MBID.getIndex()] = artist.getMbid();
        csv[Field.ARTIST_NAME.getIndex()] = artist.getName();
        csv[Field.ARTIST_HOTNESS.getIndex()] = artist.getHotness();
        csv[Field.ARTIST_GENRES.getIndex()] = artist.getGenres();
        csv[Field.ARTIST_SIMILAR_ARTISTS.getIndex()] = artist.getSimilarArtists();
        csv[Field.ALBUM_ID.getIndex()] = album.getId();
        csv[Field.ALBUM_NAME.getIndex()] = album.getName();
        csv[Field.ALBUM_YEAR.getIndex()] = album.getYear();
        csv[Field.ALBUM_HOTNESS.getIndex()] = album.getHotness();
        csv[Field.SONG_ID.getIndex()] = song.getId();
        csv[Field.SONG_NAME.getIndex()] = song.getName();
        csv[Field.SONG_DURATION.getIndex()] = song.getDuration();
        csv[Field.SONG_HOTNESS.getIndex()] = song.getHotness();
        return String.join(FIELD_DELIMITER, csv);
    }

    public static Comparator<RawRow> artistIdComparator = new Comparator<RawRow>() {

        public int compare(final RawRow row1, final RawRow row2) {

            final String artistId1 = row1.getArtist().getId().toString();
            final String artistId2 = row2.getArtist().getId().toString();

            return artistId1.compareTo(artistId2);
        }
    };

    public static Comparator<RawRow> albumIdComparator = new Comparator<RawRow>() {

        public int compare(final RawRow row1, final RawRow row2) {

            final String albumId1 = row1.getAlbum().getId().toString();
            final String albumId2 = row2.getAlbum().getId().toString();

            return albumId1.compareTo(albumId2);
        }
    };

    public static Comparator<RawRow> songIdComparator = new Comparator<RawRow>() {

        public int compare(final RawRow row1, final RawRow row2) {

            final String songId1 = row1.getSong().getId().toString();
            final String songId2 = row2.getSong().getId().toString();

            return songId1.compareTo(songId2);
        }
    };

    public static class RawRowBuilder {

        private final RawArtist artist;
        private final RawAlbum album;
        private final RawSong song;

        public RawRowBuilder(final String csvRow) {

            final String[] parts = csvRow.split(FIELD_DELIMITER);
            this.artist = new RawArtist.ArtistBuilder(parts[Field.ARTIST_ID.getIndex()],
                    parts[Field.ARTIST_MBID.getIndex()], parts[Field.ARTIST_NAME.getIndex()],
                    parts[Field.ARTIST_HOTNESS.getIndex()], parts[Field.ARTIST_GENRES.getIndex()],
                    parts[Field.ARTIST_SIMILAR_ARTISTS.getIndex()]).build();
            this.album = new RawAlbum.AlbumBuilder(parts[Field.ALBUM_ID.getIndex()], parts[Field.ALBUM_NAME.getIndex()],
                    parts[Field.ALBUM_YEAR.getIndex()], parts[Field.ALBUM_HOTNESS.getIndex()]).build();
            this.song = new RawSong.SongBuilder(parts[Field.SONG_ID.getIndex()], parts[Field.SONG_NAME.getIndex()],
                    parts[Field.SONG_DURATION.getIndex()], parts[Field.SONG_HOTNESS.getIndex()]).build();
        }

        public RawRowBuilder(final RawSong song, final RawAlbum album, final RawArtist artist) {

            this.artist = artist;
            this.album = album;
            this.song = song;
        }

        public RawRow build() {

            return new RawRow(this);
        }
    }
}
