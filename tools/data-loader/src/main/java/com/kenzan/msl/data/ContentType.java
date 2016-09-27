package com.kenzan.msl.data;

/**
 * {@link ContentType} are the available content types from the entity relationship model
 *
 * @author peterburt
 */
public enum ContentType {
    SONG("Song"), ALBUM("Album"), ARTIST("Artist");

    private String contentType;

    private ContentType(final String contentType) {

        this.contentType = contentType;
    }

    @Override
    public String toString() {

        return contentType;
    }
}
