USE msl;

// Q1:
TRUNCATE users;
COPY users FROM '../data/Q01_users.csv';

// Q2:
TRUNCATE user_ratings;
COPY user_ratings FROM '../data/Q02_user_data.csv';

// Q3:
TRUNCATE average_ratings;
SOURCE '../data/Q03_average_ratings.csv';

// Q4:
TRUNCATE featured_songs;
COPY featured_songs (hotness_bucket, content_type, hotness_value, song_id, album_id, album_name, album_year, artist_id, artist_mbid, artist_name, song_duration, song_name, image_link) FROM '../data/Q04_featured_songs.csv';

// Q5:
TRUNCATE songs_by_facet;
COPY songs_by_facet (facet_name, content_type, song_name, song_id, album_id, album_name, album_year, artist_id, artist_mbid, artist_name, song_duration, image_link) FROM '../data/Q05_songs_by_facet.csv';

// Q6:
TRUNCATE featured_albums;
COPY featured_albums FROM '../data/Q06_featured_albums.csv';

// Q7:
TRUNCATE albums_by_facet;
COPY albums_by_facet FROM '../data/Q07_albums_by_facet.csv';

// Q8:
TRUNCATE featured_artists;
COPY featured_artists FROM '../data/Q08_featured_artists.csv';

// Q9:
TRUNCATE artists_by_facet;
COPY artists_by_facet FROM '../data/Q09_artists_by_facet.csv';

// Q10:
TRUNCATE songs_albums_by_artist;
COPY songs_albums_by_artist (artist_id, album_year, album_name, album_id, song_name, song_id, artist_genres, artist_mbid, artist_name, similar_artists, song_duration, image_link) FROM '../data/Q10_songs_albums_by_artist.csv';

// Q11:
TRUNCATE songs_by_user;
COPY songs_by_user (user_id, content_type, favorites_timestamp, song_id, album_id, album_name, album_year, artist_id, artist_mbid, artist_name, song_duration, song_name, image_link) FROM '../data/Q11_songs_by_user.csv';

// Q12:
TRUNCATE albums_by_user;
COPY albums_by_user FROM '../data/Q12_albums_by_user.csv';

// Q13:
TRUNCATE artists_by_user;
COPY artists_by_user FROM '../data/Q13_artists_by_user.csv';

// Q14:
TRUNCATE songs_artist_by_album;
COPY songs_artist_by_album FROM '../data/Q14_songs_artist_by_album.csv';

// Q15:
TRUNCATE album_artist_by_song;
COPY album_artist_by_song (song_id, album_id, album_name, album_year, artist_genres, artist_id, artist_mbid, artist_name, similar_artists, song_duration, song_name, image_link) FROM '../data/Q15_album_artist_by_song.csv';
