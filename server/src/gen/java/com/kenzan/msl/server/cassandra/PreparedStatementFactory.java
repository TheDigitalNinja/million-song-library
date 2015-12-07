/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.kenzan.msl.server.cassandra.CassandraConstants.MSL_CONTENT_TYPE;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Singleton class to provide a cache of prepared statements. This will help avoid the prolific warnings from
 * Cassandra about re-preparing a prepared statement.
 *
 * @author billschwanitz
 */
public class PreparedStatementFactory {
    private static PreparedStatementFactory instance = new PreparedStatementFactory();

    private Map<PreparedStatementId, PreparedStatement> idToPreparedStatementMap = new ConcurrentHashMap<>();

    public static enum PreparedStatementId {
        ALBUM_AVERAGE_RATING_QUERY,
        ALBUM_USER_RATING_QUERY,
        ARTIST_AVERAGE_RATING_QUERY,
        ARTIST_USER_RATING_QUERY,
        SONG_AVERAGE_RATING_QUERY,
        SONG_USER_RATING_QUERY
    }

    /*
     * Private constructor to enforce singleton-ness
     */
    private PreparedStatementFactory() {
    }

    public static PreparedStatementFactory getInstance() {
        return instance;
    }

    public BoundStatement getPreparedStatement(Session session, PreparedStatementId preparedStatementId) {
        PreparedStatement preparedStatement = idToPreparedStatementMap.get(preparedStatementId);
        if (null == preparedStatement) {
            preparedStatement = buildPreparedStatement(session, preparedStatementId);

            if (preparedStatement != null) {
                idToPreparedStatementMap.put(preparedStatementId, preparedStatement);
            }
        }
        return new BoundStatement(preparedStatement);
    }

    private PreparedStatement buildPreparedStatement(Session session, PreparedStatementId preparedStatementId) {
        PreparedStatement preparedStatement = null;

        switch (preparedStatementId) {
            case ALBUM_AVERAGE_RATING_QUERY:
                preparedStatement = session.prepare(QueryBuilder.select()
                        .all()
                        .from(CassandraConstants.MSL_TABLE_AVERAGE_RATINGS)
                        .where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, QueryBuilder.bindMarker()))
                        .and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ALBUM.dbContentType)));
                break;
            case ALBUM_USER_RATING_QUERY:
                preparedStatement = session.prepare(QueryBuilder.select()
                        .all()
                        .from(CassandraConstants.MSL_TABLE_USER_DATA_BY_USER)
                        .where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_USER_ID, QueryBuilder.bindMarker()))
                        .and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ALBUM.dbContentType))
                        .and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, QueryBuilder.bindMarker())));
                break;
            case ARTIST_AVERAGE_RATING_QUERY:
                preparedStatement = session.prepare(QueryBuilder.select()
                        .all()
                        .from(CassandraConstants.MSL_TABLE_AVERAGE_RATINGS)
                        .where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, QueryBuilder.bindMarker()))
                        .and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ARTIST.dbContentType)));
                break;
            case ARTIST_USER_RATING_QUERY:
                preparedStatement = session.prepare(QueryBuilder.select()
                        .all()
                        .from(CassandraConstants.MSL_TABLE_USER_DATA_BY_USER)
                        .where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_USER_ID, QueryBuilder.bindMarker()))
                        .and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ARTIST.dbContentType))
                        .and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, QueryBuilder.bindMarker())));
                break;
            case SONG_AVERAGE_RATING_QUERY:
                preparedStatement = session.prepare(QueryBuilder.select()
                        .all()
                        .from(CassandraConstants.MSL_TABLE_AVERAGE_RATINGS)
                        .where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, QueryBuilder.bindMarker()))
                        .and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.SONG.dbContentType)));
                break;
            case SONG_USER_RATING_QUERY:
                preparedStatement = session.prepare(QueryBuilder.select()
                        .all()
                        .from(CassandraConstants.MSL_TABLE_USER_DATA_BY_USER)
                        .where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_USER_ID, QueryBuilder.bindMarker()))
                        .and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.SONG.dbContentType))
                        .and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, QueryBuilder.bindMarker())));
                break;
        }

        return preparedStatement;
    }
}
