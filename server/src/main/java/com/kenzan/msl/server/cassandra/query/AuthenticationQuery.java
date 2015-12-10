package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.Row;
import com.google.common.base.Optional;
import com.kenzan.msl.server.cassandra.QueryAccessor;

import java.util.UUID;

public class AuthenticationQuery {

    /**
     * Returns UUID of user if email and password are valid
     *
     * @param queryAccessor datastax QueryAccessor object
     * @param email user email
     * @param password user password
     * @return Optional UUID
     */
    public static Optional<UUID> authenticate(final QueryAccessor queryAccessor, String email, String password) {
        Row row = queryAccessor.logIn(email).one();
        if ( row != null ) {
            if ( row.getString("password").equals(password) ) {
                return Optional.of(row.getUUID("user_id"));
            }
        }
        return Optional.absent();
    }

}
