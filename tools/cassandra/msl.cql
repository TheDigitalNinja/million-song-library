CREATE KEYSPACE msl WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};

USE msl;

// Q1:
CREATE TABLE users (username text, creation_timestamp timestamp, password text, user_id uuid, PRIMARY KEY (username));

// Q2:
CREATE TABLE user_data_by_user (user_id uuid, content_type text, content_id uuid, favorites_timestamp timestamp, rating int, PRIMARY KEY ((user_id), content_type, content_id)) WITH CLUSTERING ORDER BY (content_type ASC, content_id ASC);

// Q3:
CREATE TABLE average_ratings (content_id uuid, num_rating COUNTER, sum_rating COUNTER, PRIMARY KEY (content_id));

// Q4:
CREATE TABLE featured_songs (hotness_bucket text, content_type text, hotness_value float, song_id uuid, album_id uuid, album_name text, album_year int, artist_id uuid, artist_name text, song_duration int, song_name text, PRIMARY KEY ((hotness_bucket, content_type), hotness_value, song_id)) WITH CLUSTERING ORDER BY (hotness_value DESC, song_id ASC);
COPY featured_songs FROM '{PATH TO data-loader DIR}Q04_featured_songs.csv' WITH header = true;

// Q5:
CREATE TABLE songs_by_facet (facet_name text, content_type text, song_name text, song_id uuid, album_id uuid, album_name text, album_year int, artist_id uuid, artist_name text, song_duration int, PRIMARY KEY ((facet_name, content_type), song_name, song_id)) WITH CLUSTERING ORDER BY (song_name ASC, song_id ASC);
COPY songs_by_facet FROM '{PATH TO data-loader DIR}Q05_songs_by_facet.csv' WITH header = true;

// Q6:
CREATE TABLE featured_albums (hotness_bucket text, content_type text, hotness_value float, album_id uuid, album_name text, album_year int, artist_id uuid, artist_name text, PRIMARY KEY ((hotness_bucket, content_type), hotness_value, album_id)) WITH CLUSTERING ORDER BY (hotness_value DESC, album_id ASC);
COPY featured_albums FROM '{PATH TO data-loader DIR}Q06_featured_albums.csv' WITH header = true;

// Q7:
CREATE TABLE albums_by_facet (facet_name text, content_type text, album_name text, album_id uuid, album_year int, artist_id uuid, artist_name text, PRIMARY KEY ((facet_name, content_type), album_name, album_id)) WITH CLUSTERING ORDER BY (album_name ASC, album_id ASC);
COPY albums_by_facet FROM '{PATH TO data-loader DIR}Q07_albums_by_facet.csv' WITH header = true;

// Q8:
CREATE TABLE featured_artists (hotness_bucket text, content_type text, hotness_value float, artist_id uuid, artist_name text, PRIMARY KEY ((hotness_bucket, content_type), hotness_value, artist_id)) WITH CLUSTERING ORDER BY (hotness_value DESC, artist_id ASC);
COPY featured_artists FROM '{PATH TO data-loader DIR}Q08_featured_artists.csv' WITH header = true;

// Q9:
CREATE TABLE artists_by_facet (facet_name text, content_type text, artist_name text, artist_id uuid, PRIMARY KEY ((facet_name, content_type), artist_name, artist_id)) WITH CLUSTERING ORDER BY (artist_name ASC, artist_id ASC);
COPY artists_by_facet FROM '{PATH TO data-loader DIR}Q09_artists_by_facet.csv' WITH header = true;

// Q10:
CREATE TABLE songs_albums_by_artist (artist_id uuid, album_year int, album_name text, song_name text, song_id uuid, album_id uuid, artist_genres SET<text> static, artist_name text static, similar_artists MAP<uuid, text> static, song_duration int, PRIMARY KEY ((artist_id), album_year, album_name, song_name, song_id)) WITH CLUSTERING ORDER BY (album_year DESC, album_name ASC, song_name ASC, song_id ASC);
COPY songs_albums_by_artist FROM '{PATH TO data-loader DIR}Q10_songs_ablums_by_artist.csv' WITH header = true;

// Q11:
CREATE TABLE songs_by_user (user_id uuid, content_type text, favorites_timestamp timestamp, song_id uuid, album_id uuid, album_name text, album_year int, artist_id uuid, artist_name text, song_duration int, song_name text, PRIMARY KEY ((user_id, content_type), favorites_timestamp, song_id)) WITH CLUSTERING ORDER BY (favorites_timestamp DESC, song_id ASC);

// Q12:
CREATE TABLE albums_by_user (user_id uuid, content_type text, favorites_timestamp timestamp, album_id uuid, album_name text, album_year int, artist_id uuid, artist_name text, PRIMARY KEY ((user_id, content_type), favorites_timestamp, album_id)) WITH CLUSTERING ORDER BY (favorites_timestamp DESC, album_id ASC);

// Q13:
CREATE TABLE artists_by_user (user_id uuid, content_type text, favorites_timestamp timestamp, artist_id uuid, artist_name text, PRIMARY KEY ((user_id, content_type), favorites_timestamp, artist_id)) WITH CLUSTERING ORDER BY (favorites_timestamp DESC, artist_id ASC);

// Q14:
CREATE TYPE paging_state (page_size int, content_type text, query text, page_state blob, end boolean, buffer list<text>);
CREATE TABLE paging_state (user_id uuid, paging_state frozen<paging_state>, PRIMARY KEY (user_id));