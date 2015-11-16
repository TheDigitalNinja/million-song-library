USE msl2;

// Q4:
TRUNCATE featured_songs;
COPY featured_songs FROM '../data/Q04_featured_songs.csv' WITH header = true;

// Q5:
TRUNCATE songs_by_facet;
COPY songs_by_facet FROM '../data/Q05_songs_by_facet.csv' WITH header = true;

// Q6:
TRUNCATE featured_albums;
COPY featured_albums FROM '../data/Q06_featured_albums.csv' WITH header = true;

// Q7:
TRUNCATE albums_by_facet;
COPY albums_by_facet FROM '../data/Q07_albums_by_facet.csv' WITH header = true;

// Q8:
TRUNCATE featured_artists;
COPY featured_artists FROM '../data/Q08_featured_artists.csv' WITH header = true;

// Q9:
TRUNCATE artists_by_facet;
COPY artists_by_facet FROM '../data/Q09_artists_by_facet.csv' WITH header = true;

// Q10:
TRUNCATE songs_albums_by_artist;
COPY songs_albums_by_artist FROM '../data/Q10_songs_ablums_by_artist.csv' WITH header = true;
