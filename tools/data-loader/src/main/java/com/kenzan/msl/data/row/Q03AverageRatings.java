/**
 * 
 */
package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;

/**
 * @author peterburt
 *
 */
public class Q03AverageRatings {
	
	private final UUID contentId;
	private final ContentType contentType;
	private final int numRating;
	private final int sumRating;

	public Q03AverageRatings(final UUID contentId, final ContentType contentType, final int numRating, final int sumRating) {
		
		this.contentId = contentId;
		this.contentType = contentType;
		this.numRating = numRating;
		this.sumRating = sumRating;
	}

    public String toString() {
		
		final List<String> AverageRating = new ArrayList<String>();
		AverageRating.add(contentId.toString());
		AverageRating.add(contentType.toString());
		AverageRating.add(Integer.toString(numRating));
        AverageRating.add(Integer.toString(sumRating));
		return String.join(",", AverageRating);
	}
}
