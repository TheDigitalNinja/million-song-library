package com.kenzan.msl.data.row;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Q01User {
	
	private final UUID userId;
	private final String username;
	private final String password;
	private final Date timestamp;

	public Q01User(final UUID userId, final String username, final String password, final Date timestamp) {
		
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.timestamp = timestamp;
	}

	public UUID getId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public String toString() {
		
		final List<String> user = new ArrayList<String>();
		user.add(RowUtil.formatText(username));
        user.add(RowUtil.formatTimestamp(timestamp));
		user.add(RowUtil.formatText(password));
        user.add(userId.toString());
		return String.join(RowUtil.FIELD_DELIMITER, user);
	}
}
