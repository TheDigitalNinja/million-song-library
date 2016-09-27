/**
 * 
 */
package com.kenzan.msl.data;

/**
 * {@link Field} matches with the corresponding index from the python script csv output
 *
 * @author peterburt
 */
public enum Field {

    SONG_ID(0),
    SONG_NAME(1),
    SONG_DURATION(2),
    SONG_HOTNESS(3),
    ALBUM_ID(4),
    ALBUM_NAME(5),
    ALBUM_YEAR(6),
    ALBUM_HOTNESS(7),
    ARTIST_ID(8),
    ARTIST_MBID(9),
    ARTIST_NAME(10),
    ARTIST_HOTNESS(11),
    ARTIST_GENRES(12),
    ARTIST_SIMILAR_ARTISTS(13);

    private int index;

    private Field(final int index) { this.index = index; }

    public int getIndex() { return index; }
}
