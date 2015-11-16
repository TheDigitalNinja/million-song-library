/**
 * 
 */
package com.kenzan.msl.data;

import java.util.UUID;

/**
 * @author peterburt
 *
 */
public class NormalizedSong {

    private final UUID id;
    private final String name;
    private final int duration;
    private final float hotness;

    private NormalizedSong(SongBuilder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.duration = builder.duration;
        this.hotness = builder.hotness;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public float getHotness() {
        return hotness;
    }

    public static class SongBuilder {

        private UUID id;
        private String name;
        private int duration;
        private float hotness;

        public SongBuilder(final UUID id, final String name, final int duration, final float hotness) {

            this.id = id;
            this.name = name;
            this.duration = duration;
            this.hotness = hotness;
        }

        public NormalizedSong build() {

            return new NormalizedSong(this);
        }
    }
}
