package com.kenzan.msl.data;

import java.util.UUID;

public class NormalizedAlbum {

    private final UUID id;
    private final String name;
    private final int year;
    private final float hotness;

    private NormalizedAlbum(AlbumBuilder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.year = builder.year;
        this.hotness = builder.hotness;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public float getHotness() {
        return hotness;
    }

    public static class AlbumBuilder {

        private UUID id;
        private String name;
        private int year;
        private float hotness;

        public AlbumBuilder(final UUID id, final String name, final int year, final float hotness) {

            this.id = id;
            this.name = name;
            this.year = year;
            this.hotness = hotness;
        }

        public NormalizedAlbum build() {

            return new NormalizedAlbum(this);
        }
    }
}
