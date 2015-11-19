package com.kenzan.msl.data;

public class RawSong {

    private final String id;
    private final String name;
    private final String duration;
    private final String hotness;

    private RawSong(SongBuilder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.duration = builder.duration;
        this.hotness = builder.hotness;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getHotness() {
        return hotness;
    }

    public static class SongBuilder {

        private String id;
        private String name;
        private String duration;
        private String hotness;

        public SongBuilder(final String id, final String name, final String duration, final String hotness) {

            this.id = id;
            this.name = name;
            this.duration = duration;
            this.hotness = hotness;
        }

        public RawSong build() {

            return new RawSong(this);
        }
    }
}
