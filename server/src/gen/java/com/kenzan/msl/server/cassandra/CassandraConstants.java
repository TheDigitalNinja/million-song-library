/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.cassandra;

import com.kenzan.msl.server.bo.AlbumListBo;
import com.kenzan.msl.server.bo.ArtistListBo;
import com.kenzan.msl.server.bo.AbstractBo;
import com.kenzan.msl.server.bo.AbstractListBo;
import com.kenzan.msl.server.bo.SongListBo;
import com.kenzan.msl.server.dao.AbstractAlbumDao;
import com.kenzan.msl.server.dao.AbstractArtistDao;
import com.kenzan.msl.server.dao.AbstractDao;
import com.kenzan.msl.server.dao.AbstractSongDao;
import com.kenzan.msl.server.dao.AlbumsByFacetDao;
import com.kenzan.msl.server.dao.ArtistsByFacetDao;
import com.kenzan.msl.server.dao.FeaturedAlbumsDao;
import com.kenzan.msl.server.dao.FeaturedArtistsDao;
import com.kenzan.msl.server.dao.FeaturedSongsDao;
import com.kenzan.msl.server.dao.SongsByFacetDao;

/**
 *
 *
 * @author billschwanitz
 */
public class CassandraConstants {

	public static final String MSL_KEYSPACE = "msl";
	
	public static final String MSL_TABLE_USERS 					= "users";					// Q1
	public static final String MSL_TABLE_USER_DATA_BY_USER		= "user_data_by_user";		// Q2
	public static final String MSL_TABLE_AVERAGE_RATINGS		= "average_ratings";		// Q3
	public static final String MSL_TABLE_FEATURED_SONGS			= "featured_songs";			// Q4
	public static final String MSL_TABLE_SONGS_BY_FACET			= "songs_by_facet";			// Q5
	public static final String MSL_TABLE_FEATURED_ALBUMS		= "featured_albums";		// Q6
	public static final String MSL_TABLE_ALBUMS_BY_FACET		= "albums_by_facet";		// Q7
	public static final String MSL_TABLE_FEATURED_ARTISTS		= "featured_artists";		// Q8
	public static final String MSL_TABLE_ARTISTS_BY_FACET		= "artists_by_facet";		// Q9
	public static final String MSL_TABLE_SONGS_ALBUMS_BY_ARTIST	= "songs_albums_by_artist";	// Q10
	public static final String MSL_TABLE_SONGS_BY_USER			= "songs_by_user";			// Q11
	public static final String MSL_TABLE_ALBUMS_BY_USER			= "albums_by_user";			// Q12
	public static final String MSL_TABLE_ARTISTS_BY_USER		= "artists_by_user";		// Q13
	public static final String MSL_TABLE_SONGS_ARTIST_BY_ALBUM	= "songs_artist_by_album";	// Q14
	public static final String MSL_TABLE_ALBUM_ARTIST_BY_SONG	= "album_artist_by_song";	// Q15
	public static final String MSL_TABLE_PAGING_STATE			= "paging_state";			// Q16
	
	public static final int MSL_BROWSE_MIN_PAGE_SIZE		= 1;
	public static final int MSL_BROWSE_MAX_PAGE_SIZE		= 100;
	public static final int MSL_BROWSE_DEFAULT_PAGE_SIZE	= 25;
	
	public static final String MSL_DEFAULT_HOTNESS_BUCKET	= "Hotness01";

	public static final String MSL_COLUMN_ARTIST_ID			= "artist_id";
	public static final String MSL_COLUMN_CONTENT_ID		= "content_id";
	public static final String MSL_COLUMN_CONTENT_TYPE		= "content_type";
	public static final String MSL_COLUMN_FACET_NAME		= "facet_name";
	public static final String MSL_COLUMN_HOTNESS_BUCKET	= "hotness_bucket";
	public static final String MSL_COLUMN_USER_ID			= "user_id";

	public static enum MSL_CONTENT_TYPE {
		ALBUM	("Album", MSL_TABLE_FEATURED_ALBUMS, FeaturedAlbumsDao.class, MSL_TABLE_ALBUMS_BY_FACET, AlbumsByFacetDao.class, AbstractAlbumDao.class, AlbumListBo.class),
		ARTIST	("Artist", MSL_TABLE_FEATURED_ARTISTS, FeaturedArtistsDao.class, MSL_TABLE_ARTISTS_BY_FACET, ArtistsByFacetDao.class, AbstractArtistDao.class, ArtistListBo.class),
		SONG	("Song", MSL_TABLE_FEATURED_SONGS, FeaturedSongsDao.class, MSL_TABLE_SONGS_BY_FACET, SongsByFacetDao.class, AbstractSongDao.class, SongListBo.class);
		
		public final String dbContentType;
		public final String featuredTableName;
		public final Class<? extends AbstractDao> featuredContentDaoClass;
		public final String facetTableName;
		public final Class<? extends AbstractDao> facetContentDaoClass;
		public final Class<? extends AbstractDao> commonContentDaoClass;
		public final Class<? extends AbstractListBo<? extends AbstractBo>> contentListDaoClass;
		
		MSL_CONTENT_TYPE(String dbContentType,
				String featuredTableName, Class<? extends AbstractDao> featuredContentDaoClass,
				String facetTableName, Class<? extends AbstractDao> facetContentDaoClass,
				Class<? extends AbstractDao> commonContentDaoClass,
				Class<? extends AbstractListBo<? extends AbstractBo>> contentListDaoClass) {
			this.dbContentType = dbContentType;
			this.featuredTableName = featuredTableName;
			this.featuredContentDaoClass = featuredContentDaoClass;
			this.facetTableName = facetTableName;
			this.facetContentDaoClass = facetContentDaoClass;
			this.commonContentDaoClass = commonContentDaoClass;
			this.contentListDaoClass = contentListDaoClass;
		}

	}
}
