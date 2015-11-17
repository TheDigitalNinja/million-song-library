package com.kenzan.msl.data;

/**
 * @author peterburt
 *
 */
public enum Rating {
    ONE("1 & UP"), TWO("2 & UP"), THREE("3 & UP"), FOUR("4 & UP"), FIVE("4 & UP");

    private String facetName;

    private Rating(final String facetName) {

        this.facetName = facetName;
    }

    @Override
    public String toString() {

        return facetName;
    }
}
