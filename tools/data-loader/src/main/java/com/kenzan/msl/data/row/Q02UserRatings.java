package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.kenzan.msl.data.ContentType;

public class Q02UserRatings {
	
	private final UUID userId;
	private final ContentType contentType;
	private final UUID contentId;
	private final int rating;

	public Q02UserRatings(final UUID userId, final ContentType contentType, final UUID contentId, final int rating) {
		
		this.userId = userId;
		this.contentType = contentType;
		this.contentId = contentId;
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

    public int getRating() {
        return rating;
    }

    public String toString() {
		
		final List<String> userData = new ArrayList<String>();
		userData.add(userId.toString());
		userData.add(contentType.toString());
		userData.add(contentId.toString());
		userData.add(RowUtil.formatInt(rating));
		return String.join(RowUtil.FIELD_DELIMITER, userData);
	}
}
