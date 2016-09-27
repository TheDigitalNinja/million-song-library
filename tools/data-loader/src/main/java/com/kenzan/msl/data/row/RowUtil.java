package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.kenzan.msl.data.Genre;

/**
 * {@link RowUtil} contains methods for formatting CSV data compatible with
 * Cassandra cqlsh copy command
 *
 * @author peterburt
 */
public class RowUtil {

    public static final String FIELD_DELIMITER = ",";

    public static String formatHotness(final float hotness) {

        return String.format("%.12f", hotness);
    }

    /**
     * @param text
     *            {@link String} that may contain quotes
     * @return {@link String} with quotes escaped with backslash
     */
    public static String formatText(final String text) {

        return String.format("\"%s\"", text.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\""));
    }

    /**
     * @param text
     *            {@link String} that may contain quotes
     * @return {@link String} with double quote escaped with backslash and
     *         single quote escaped with single quote
     */
    public static String formatTextInCollection(final String text) {

        return String.format("\'%s\'", text.replaceAll("'", "\'\'").replaceAll("\"", "\\\\\""));
    }

    /**
     * @param year
     *            {@link int} year
     * @return {@link String} must return a value rather then an empty string
     *         where used as a clustering column
     */
    public static String formatYear(final int year) {

        return String.format("%04d", year);
    }

    public static String formatInt(final int num) {

        if (num > 0) {
            return Integer.toString(num);
        }
        return "";
    }

    public static String formatTimestamp(final Date date) {

        if (date != null) {
            return Long.toString(date.getTime());
        }
        return "";
    }

    public static String formatGenres(final List<Genre> artistGenres) {

        final List<String> genres = new ArrayList<String>();
        for (final Genre genre : artistGenres) {
            genres.add(RowUtil.formatTextInCollection(genre.toString()));
        }
        return String.format("\"{%s}\"", String.join(", ", genres));
    }

    public static String formatSimilarArtists(final Map<UUID, String> similarArtists) {

        final List<String> similars = new ArrayList<String>();
        for (final UUID id : similarArtists.keySet()) {
            similars.add(id.toString() + ": " + RowUtil.formatTextInCollection(similarArtists.get(id)));
        }
        return String.format("\"{%s}\"", String.join(", ", similars));
    }
}
