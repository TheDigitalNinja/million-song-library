/**
 * 
 */
package com.kenzan.msl.data;

/**
 * @author peterburt
 *
 */
public class RawArtist {

    private final String id;
    private final String mbid;
    private final String name;
    private final String hotness;
    private final String genres;
    private final String similarArtists;

    private RawArtist(ArtistBuilder builder) {

        this.id = builder.id;
        this.mbid = builder.mbid;
        this.name = builder.name;
        this.hotness = builder.hotness;
        this.genres = builder.genres;
        this.similarArtists = builder.similarArtists;
    }

    public String getId() {
        return id;
    }

    public String getMbid() {
        return mbid;
    }

    public String getName() {
        return name;
    }

    public String getHotness() {
        return hotness;
    }

    public String getGenres() {
        return genres;
    }

    public String getSimilarArtists() {
        return similarArtists;
    }

    public static class ArtistBuilder {

        private final String id;
        private final String mbid;
        private final String name;
        private final String hotness;
        private final String genres;
        private final String similarArtists;

        public ArtistBuilder(final String id, final String mbid, final String name, final String hotness,
                final String genres, final String similarArtists) {

            this.id = id;
            this.mbid = mbid;
            this.name = name;
            this.hotness = hotness;
            this.genres = genres;
            this.similarArtists = similarArtists;
        }

        public RawArtist build() {

            return new RawArtist(this);
        }
    }
}
