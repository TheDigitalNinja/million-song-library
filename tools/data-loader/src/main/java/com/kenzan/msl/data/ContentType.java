package com.kenzan.msl.data;

/**
 * @author peterburt
 *
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
