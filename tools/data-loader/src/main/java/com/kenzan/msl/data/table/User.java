/**
 * 
 */
package com.kenzan.msl.data.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author peterburt
 *
 */
public class User {
	
	private final UUID id;
	private final String username;
	private final String password;
	private final Date timestamp;

	private User(final UserBuilder builder) {
		
		this.id = builder.id;
		this.username = builder.username;
		this.password = builder.password;
		this.timestamp = builder.timestamp;
	}

	public UUID getId() {
		return id;
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
		user.add(id.toString());
		user.add(username);
		user.add(password);
		user.add(timestamp.toString());
		return String.join(",", user);
	}
	
	public static class UserBuilder {
		
		private final UUID id;
		private final String username;
		private final String password;
		private final Date timestamp;
		
		public UserBuilder(final UUID id, final String username, final String password, final Date timestamp) {
			
			this.id = id;
			this.username = username;
			this.password = password;
			this.timestamp = timestamp;
		}
		
		public User build() {
			
			return new User(this);
		}
		
	}
}
