package com.kenzan.msl.data.row;

import java.util.StringJoiner;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;

public class Q03AverageRatings {

    private final UUID contentId;
    private final ContentType contentType;
    private final int numRating;
    private final int sumRating;

    public Q03AverageRatings(final UUID contentId, final ContentType contentType, final int numRating,
            final int sumRating) {

        this.contentId = contentId;
        this.contentType = contentType;
        this.numRating = numRating;
        this.sumRating = sumRating;
    }

    public String toString() {

        final StringJoiner statement = new StringJoiner(" ");
        statement.add("UPDATE msl.average_ratings SET");
        statement.add("num_rating = num_rating +");
        statement.add(String.valueOf(numRating));
        statement.add(",");
        statement.add("sum_rating = sum_rating +");
        statement.add(String.valueOf(sumRating));
        statement.add("WHERE content_id =");
        statement.add(contentId.toString());
        statement.add("AND");
        statement.add("content_type =");
        statement.add(String.format("\'%s\'", contentType.toString()));
        statement.add(";");

        return statement.toString();
    }
}
