package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.Row;
import com.kenzan.msl.server.cassandra.QueryAccessor;

import java.util.UUID;

public class AuthenticationQuery {

    /**
     * Returns UUID of user if email and password are valid
     *
     * @param queryAccessor    datastax QueryAccessor object
     * @param email            user email
     * @param password         user password
     * @return UUID
     */
    public static UUID authenticate (final QueryAccessor queryAccessor, String email, String password) {
        Row row = queryAccessor.logIn(email).one();
        if (row != null) {
            if (row.getString("password").equals(password)) {
                return row.getUUID("user_id");
            }
        }
        return new UUID(0L, 0L);
    }

}
