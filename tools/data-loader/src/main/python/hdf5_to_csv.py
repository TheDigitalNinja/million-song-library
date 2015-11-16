import sys
import hdf5_getters
import string

def filter_string(value):
  include = []
  include.extend(string.printable)
  include.remove(',')
  include.remove('|')
  value = ''.join([x for x in value if x in include])
  return value

def clean_value(value):
  dirty = ['0', '0.0', 'nan']
  if value in dirty:
    return ''
  return value

def main(argv):
  h5 = hdf5_getters.open_h5_file_read(argv[0])
  song_id = hdf5_getters.get_song_id(h5)
  song_name = filter_string(hdf5_getters.get_title(h5))
  song_duration = clean_value(str(int(hdf5_getters.get_duration(h5))))
  song_hotness = clean_value(str(hdf5_getters.get_song_hotttnesss(h5)))
  album_id = clean_value(str(hdf5_getters.get_release_7digitalid(h5)))
  album_name = filter_string(hdf5_getters.get_release(h5))
  album_year = clean_value(str(hdf5_getters.get_year(h5)))
  album_hotness = ''
  artist_id = hdf5_getters.get_artist_id(h5)
  artist_mbid = clean_value(str(hdf5_getters.get_artist_mbid(h5)))
  artist_name = filter_string(hdf5_getters.get_artist_name(h5))
  artist_hotness = clean_value(str(hdf5_getters.get_artist_hotttnesss(h5)))
  artist_genres = '|'.join(hdf5_getters.get_artist_terms(h5))
  similar_artists = '|'.join(hdf5_getters.get_similar_artists(h5))
  h5.close()

  song_data = [song_id, song_name, song_duration, song_hotness, album_id, album_name, album_year, album_hotness, artist_id, artist_mbid, artist_name, artist_hotness, artist_genres, similar_artists]
  print ','.join(song_data)

if __name__ == "__main__":
   main(sys.argv[1:])
