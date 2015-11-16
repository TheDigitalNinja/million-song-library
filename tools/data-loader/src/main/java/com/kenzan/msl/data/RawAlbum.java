/**
 * 
 */
package com.kenzan.msl.data;

/**
 * @author peterburt
 *
 */
public class RawAlbum {

    private final String id;
    private final String name;
    private final String year;
    private final String hotness;

    private RawAlbum(AlbumBuilder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.year = builder.year;
        this.hotness = builder.hotness;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getHotness() {
        return hotness;
    }

    public static class AlbumBuilder {

        private final String id;
        private final String name;
        private final String year;
        private final String hotness;

        public AlbumBuilder(final String id, final String name, final String year, final String hotness) {

            this.id = id;
            this.name = name;
            this.year = year;
            this.hotness = hotness;
        }

        public RawAlbum build() {

            return new RawAlbum(this);
        }
    }
}
