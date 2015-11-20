package com.kenzan.msl.data;

import java.util.List;
import java.util.UUID;

public class NormalizedArtist {

    private final UUID id;
    private final UUID mbid;
    private final String name;
    private final float hotness;
    private final List<Genre> genres;
    private final List<String> similarArtists;

    private NormalizedArtist(ArtistBuilder builder) {

        this.id = builder.id;
        this.mbid = builder.mbid;
        this.name = builder.name;
        this.hotness = builder.hotness;
        this.genres = builder.genres;
        this.similarArtists = builder.similarArtists;
    }

    public UUID getId() {
        return id;
    }

    public UUID getMbid() {
        return mbid;
    }

    public String getName() {
        return name;
    }

    public float getHotness() {
        return hotness;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<String> getSimilarArtists() {
        return similarArtists;
    }

    public static class ArtistBuilder {

        private final UUID id;
        private final UUID mbid;
        private final String name;
        private final float hotness;
        private final List<Genre> genres;
        private final List<String> similarArtists;

        public ArtistBuilder(final UUID id, final UUID mbid, final String name, final float hotness,
                final List<Genre> genres, final List<String> similarArtists) {

            this.id = id;
            this.mbid = mbid;
            this.name = name;
            this.hotness = hotness;
            this.genres = genres;
            this.similarArtists = similarArtists;
        }

        public NormalizedArtist build() {

            return new NormalizedArtist(this);
        }
    }
}
