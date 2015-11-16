package com.kenzan.msl.data;

/**
 * @author peterburt
 *
 */
public enum Genre {
    ALTERNATIVE("Alternative"), BIG_BAND("Big Band"), BLUEGRASS("Bluegrass"), BLUES("Blues"), CAJUN("Cajun"), CELTIC(
            "Celtic"), CLASSICAL("Classical"), COUNTRY("Country"), DUBSTEP("Dubstep"), ELECTRONICA("Electronica"), FOLK(
                    "Folk"), FUNK("Funk"), GOSPEL("Gospel"), HEAVY_METAL("Heavy Metal"), HIP_HOP("Hip Hop"), JAZZ(
                            "Jazz"), LATIN("Latin"), OPERA("Opera"), POP("Pop"), PUNK("Punk"), RAP("Rap"), REGGAE(
                                    "Reggae"), ROCK("Rock"), SALSA("Salsa"), SOUL("Soul");

    private String genre;

    private Genre(final String genre) {

        this.genre = genre;
    }

    @Override
    public String toString() {

        return genre;
    }
}
