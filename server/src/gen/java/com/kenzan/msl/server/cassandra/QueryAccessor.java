package com.kenzan.msl.server.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface QueryAccessor {
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    public ResultSet logIn(@Param("username") String username);
}
