/**
 * 
 */
package com.kenzan.msl.data.row;

import java.util.Date;

/**
 * @author peterburt
 *
 */
public class RowUtil {

    public static String formatHotness(final float hotness) {

        return String.format("%.12f", hotness);
    }

    public static String formatText(final String name) {

        return String.format("\"%s\"", name.replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\""));
    }

    public static String formatTextInCollection(final String name) {

        return String.format("\'%s\'", name.replaceAll("'", "\'\'").replaceAll("\"", "\\\\\""));
    }

    public static String formatYear(final int year) {

        return String.format("%04d", year);
    }

    public static Date randomDate() {

        return new Date(0L + (long) (Math.random() * (new Date().getTime() - 0L)));
    }

    public static String formatTimestamp(final Date date) {

        return Long.toString(date.getTime());
    }

}
