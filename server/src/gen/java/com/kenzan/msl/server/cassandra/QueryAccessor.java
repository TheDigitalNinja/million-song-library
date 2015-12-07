package com.kenzan.msl.server.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

import java.util.UUID;

@Accessor
public interface QueryAccessor {
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    public ResultSet logIn(@Param("username") String username);

    @Query ("SELECT * FROM songs_by_user WHERE user_id = :user_id AND content_type='Song'")
    public ResultSet songsByUser(@Param("user_id")UUID user_id);

    @Query ("SELECT * FROM albums_by_user WHERE user_id = :user_id AND content_type='Album'")
    public ResultSet albumsByUser(@Param("user_id")UUID user_id);

    @Query ("SELECT * FROM artists_by_user WHERE user_id = :user_id AND content_type='Artist'")
    public ResultSet artistsByUser(@Param("user_id")UUID user_id);
}
