/**
 * 
 */
package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;

/**
 * @author peterburt
 *
 */
public class Q02UserData {
	
	private final UUID userId;
	private final ContentType contentType;
	private final UUID contentId;
    private final Date favoritesTimestamp;
	private final int rating;

	public Q02UserData(final UUID userId, final ContentType contentType, final UUID contentId, final Date favoritesTimestamp, final int rating) {
		
		this.userId = userId;
		this.contentType = contentType;
		this.contentId = contentId;
		this.favoritesTimestamp = favoritesTimestamp;
		this.rating = rating;
	}

	public UUID getId() {
		return userId;
	}
	
	public ContentType getContentType() {
        return contentType;
    }

    public UUID getContentId() {
        return contentId;
    }

    public Date getFavoritesTimestamp() {
        return favoritesTimestamp;
    }

    public int getRating() {
        return rating;
    }

    public String toString() {
		
		final List<String> userData = new ArrayList<String>();
		userData.add(userId.toString());
		userData.add(contentType.toString());
		userData.add(contentId.toString());
		if (favoritesTimestamp != null) {
		    userData.add(RowUtil.formatTimestamp(favoritesTimestamp));
		} else {
		    userData.add("");
		}
		if (rating > 0) {
		    userData.add(Integer.toString(rating));
		} else {
		    userData.add("");
		}
		return String.join(",", userData);
	}
}
