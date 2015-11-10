import time
import os
import hdf5_getters
import string
import uuid

#
# Read hdf5 directories for full hdf5 file list
#
def recursive_read(directory, write_file):
  listings = os.listdir(directory)
  for listing in listings:
    path = os.path.join(directory, listing)
    if os.path.isdir(path):
      recursive_read(path, write_file)
    elif path.endswith('.h5'):
      write_file.write('{}\n'.format(path))

def create_hdf5_files_list(root_directory, write_file_path):
  with open(write_file_path, 'w') as hdf5_file_list:
    recursive_read(root_directory, hdf5_file_list)

#
# Clean hdf5 data
#
def filter_string(value):
  include = []
  include.extend(string.printable)
  include.remove(',')
  include.remove('|')
  value = ''.join([x for x in value if x in include])
  value = value.replace("'", r"\'")
  value = value.replace('"', r'\"')
  return value

def clean_value(value):
  dirty = ['0', '0.0', 'nan']
  if value in dirty:
    return ''
  return value

def format_float(string):
  if string:
    string = '%.12f' % float(string)
  return string

def filter_genres(genres):
  approved = ['alternative','big band','bluegrass','blues','cajun','celtic','classical','country','dubstep','electronica','folk','funk','gospel','heavy metal','hip hop','jazz','latin','opera','pop','punk','rap','reggae','rock','salsa','soul']
  return '|'.join([x for x in genres if x in approved])

#
# write data
#
def write_data(data_to_write, write_file_path, limit):
  with open(write_file_path, 'a') as write_file:
    for line in data_to_write[:limit]:
      write_file.write('{}\n'.format(line))
  return data_to_write[limit:]

#
# data manipulation: extract data from hdf5 file
#
def extract_hdf5(line, data_to_write, write_file_path, limit, eof):
  h5 = hdf5_getters.open_h5_file_read(line)
  print(line)
  song_id = hdf5_getters.get_song_id(h5)
  song_name = filter_string(hdf5_getters.get_title(h5))
  song_duration = clean_value(str(int(hdf5_getters.get_duration(h5))))
  song_hotness = format_float(clean_value(str(hdf5_getters.get_song_hotttnesss(h5))))
  album_id = clean_value(str(hdf5_getters.get_release_7digitalid(h5)))
  album_name = filter_string(hdf5_getters.get_release(h5))
  album_year = clean_value(str(hdf5_getters.get_year(h5)))
  album_hotness = ''
  artist_id = hdf5_getters.get_artist_id(h5)
  artist_name = filter_string(hdf5_getters.get_artist_name(h5))
  artist_hotness = format_float(clean_value(str(hdf5_getters.get_artist_hotttnesss(h5))))
  artist_genres = filter_genres(hdf5_getters.get_artist_terms(h5))
  similar_artists = '|'.join(hdf5_getters.get_similar_artists(h5))
  h5.close()

  if song_name and album_name and artist_name:
    song_data = [song_id, song_name, song_duration, song_hotness, album_id, album_name, album_year, album_hotness, artist_id, artist_name, artist_hotness, artist_genres, similar_artists]
    data_to_write.append(','.join(song_data))
  else:
    print 'BROKEN: ^'

  if len(data_to_write) == limit or eof:
    data_to_write = write_data(data_to_write, write_file_path, limit)

  return data_to_write

#
# data manipulation: replace song ids
#
def replace_song_id_last_group(data_to_write, index):
  has_same_id = True
  common_lines = []
  popped = data_to_write.pop(-1)
  popped_parts = popped.split(',')
  while has_same_id:
    common_lines.append(popped)
    if data_to_write:
      last_line = data_to_write[-1]
      last_line_parts = last_line.split(',')
      has_same_id = True if popped_parts[index] == last_line_parts[index] else False
      if has_same_id:
        popped = data_to_write.pop(-1)
        popped_parts = popped.split(',')
    else:
      has_same_id = False
  new_id = str(uuid.uuid4())
  for common_line in common_lines:
    common_line_parts = common_line.split(',')
    common_line_parts.pop(index)
    common_line_parts.insert(index, new_id)
    data_to_write.append(','.join(common_line_parts))

  return data_to_write

def replace_song_id(line, data_to_write, write_file_path, limit, eof, index):
  skip = False
  if data_to_write:
    line_parts = line.split(',')
    last_line = data_to_write[-1]
    last_line_parts = last_line.split(',')
    # if the last line of the data to write has the same id as the line to add then append line
    if line_parts[index] == last_line_parts[index]:
      skip = True
      data_to_write.append(line)
    # else grab last group and replace id
    else:
      replace_song_id_last_group(data_to_write, index)
      # append line
      data_to_write.append(line)
      if len(data_to_write) == limit:
        skip = True
    # if end of file grab last group and replace id
    if eof:
      replace_song_id_last_group(data_to_write, index)

    if (len(data_to_write) >= limit and not skip) or eof:
      data_to_write = write_data(data_to_write, write_file_path, limit)
  else:
    data_to_write.append(line)

  return data_to_write

#
# data manipulation: replace artist ids
#
def replace_artist_id_last_group(data_to_write, id_index, name_index, artist_hotness_index, genres_index, ref_file_path):
  has_same_id = True
  common_lines = []
  artist_names = []
  artist_hotness_values = []
  all_genres = []
  popped = data_to_write.pop(-1)
  popped_parts = popped.split(',')
  while has_same_id:
    artist_names.append(popped_parts[name_index])
    if popped_parts[artist_hotness_index]:
      artist_hotness_values.append(float(popped_parts[artist_hotness_index]))
    if popped_parts[genres_index]:
      all_genres.extend(popped_parts[genres_index].split('|'))
    common_lines.append(popped)
    if data_to_write:
      last_line = data_to_write[-1]
      last_line_parts = last_line.split(',')
      has_same_id = True if popped_parts[id_index] == last_line_parts[id_index] else False
      if has_same_id:
        popped = data_to_write.pop(-1)
        popped_parts = popped.split(',')
    else:
      has_same_id = False
  new_id = str(uuid.uuid4())
  artist_name = min(artist_names, key=len)
  artist_hotness = format_float('0')
  genres = ''
  if artist_hotness_values:
    artist_hotness = format_float(sum(artist_hotness_values)/len(artist_hotness_values))
  if all_genres:
    genres = '|'.join(sorted(list(set(all_genres))))
  artist_old_id = popped_parts[id_index]
  for common_line in common_lines:
    common_line_parts = common_line.split(',')
    common_line_parts.pop(id_index)
    common_line_parts.insert(id_index, new_id)
    common_line_parts.pop(name_index)
    common_line_parts.insert(name_index, artist_name)
    common_line_parts.pop(artist_hotness_index)
    common_line_parts.insert(artist_hotness_index, artist_hotness)
    common_line_parts.pop(genres_index)
    common_line_parts.insert(genres_index, genres)
    data_to_write.append(','.join(common_line_parts))
  with open(ref_file_path, 'a') as ref_file:
    ref_file.write(artist_old_id + ',' + new_id + ': ' + "'%s'" % artist_name.replace("\\'", "''") + '\n')

  return data_to_write

def replace_artist_id(line, data_to_write, write_file_path, limit, eof, id_index, name_index, artist_hotness_index, genres_index, ref_file_path):
  skip = False
  if data_to_write:
    line_parts = line.split(',')
    last_line = data_to_write[-1]
    last_line_parts = last_line.split(',')
    # if the last line of the data to write has the same id as the line to add then append line
    if line_parts[id_index] == last_line_parts[id_index]:
      skip = True
      data_to_write.append(line)
    # else grab last group and replace id
    else:
      replace_artist_id_last_group(data_to_write, id_index, name_index, artist_hotness_index, genres_index, ref_file_path)
      # append line
      data_to_write.append(line)
      if len(data_to_write) == limit:
        skip = True
    # if end of file grab last group and replace id
    if eof:
      replace_artist_id_last_group(data_to_write, id_index, name_index, artist_hotness_index, genres_index, ref_file_path)

    if (len(data_to_write) >= limit and not skip) or eof:
      data_to_write = write_data(data_to_write, write_file_path, limit)
  else:
    data_to_write.append(line)

  return data_to_write

#
# data manipulation: replace similar artists
#

def replace_similar_artists(line, data_to_write, write_file_path, limit, eof, index, lookup):
  line_parts = line.split(',')
  similar_artists = line_parts.pop(index)
  similar_artists_list = sorted(similar_artists.split('|'))
  for idx, val in enumerate(similar_artists_list):
    new_id_name = lookup.get(val)
    if new_id_name is not None:
      similar_artists_list.pop(idx)
      similar_artists_list.insert(idx, new_id_name)
  similar_artists_list = [x for x in similar_artists_list if not x.startswith('AR')]
  similar_artists = '|'.join(similar_artists_list)
  line_parts.insert(index, similar_artists)
  line = ','.join(line_parts)
  data_to_write.append(line)
  if len(data_to_write) == limit or eof:
    data_to_write = write_data(data_to_write, write_file_path, limit)

  return data_to_write

#
# data manipulation: replace album ids
#
def replace_album_id_last_group(data_to_write, id_index, song_hotness_index, album_hotness_index, album_year_index):
  has_same_id = True
  common_lines = []
  song_hotness_values = []
  album_year_values = []
  popped = data_to_write.pop(-1)
  popped_parts = popped.split(',')
  while has_same_id:
    if popped_parts[song_hotness_index]:
      song_hotness_values.append(float(popped_parts[song_hotness_index]))
    if popped_parts[album_year_index]:
      album_year_values.append(int(popped_parts[album_year_index]))
    common_lines.append(popped)
    if data_to_write:
      last_line = data_to_write[-1]
      last_line_parts = last_line.split(',')
      has_same_id = True if popped_parts[id_index] == last_line_parts[id_index] else False
      if has_same_id:
        popped = data_to_write.pop(-1)
        popped_parts = popped.split(',')
    else:
      has_same_id = False
  new_id = str(uuid.uuid4())
  album_hotness = format_float('0')
  album_year = '0000'
  if song_hotness_values:
    album_hotness = format_float(sum(song_hotness_values)/len(song_hotness_values))
  if album_year_values:
    album_year = str(max(album_year_values))
  for common_line in common_lines:
    common_line_parts = common_line.split(',')
    common_line_parts.pop(id_index)
    common_line_parts.insert(id_index, new_id)
    common_line_parts.pop(album_hotness_index)
    common_line_parts.insert(album_hotness_index, album_hotness)
    common_line_parts.pop(album_year_index)
    common_line_parts.insert(album_year_index, album_year)
    data_to_write.append(','.join(common_line_parts))

  return data_to_write

def replace_album_id(line, data_to_write, write_file_path, limit, eof, id_index, song_hotness_index, album_hotness_index, album_year_index):
  skip = False
  if data_to_write:
    line_parts = line.split(',')
    last_line = data_to_write[-1]
    last_line_parts = last_line.split(',')
    # if the last line of the data to write has the same id as the line to add then append line
    if line_parts[id_index] == last_line_parts[id_index]:
      skip = True
      data_to_write.append(line)
    # else grab last group and replace id
    else:
      replace_album_id_last_group(data_to_write, id_index, song_hotness_index, album_hotness_index, album_year_index)
      # append line
      data_to_write.append(line)
      if len(data_to_write) == limit:
        skip = True
    # if end of file grab last group and replace id
    if eof:
      replace_album_id_last_group(data_to_write, id_index, song_hotness_index, album_hotness_index, album_year_index)

    if (len(data_to_write) >= limit and not skip) or eof:
      data_to_write = write_data(data_to_write, write_file_path, limit)
  else:
    data_to_write.append(line)

  return data_to_write

def filter_genre(genre):
  return "'%s'" % genre.capitalize()

def generate_genres_list(genres):
  genre_list = []
  if genres:
    genres = genres.split('|')
    for genre in genres:
      genre = filter_genre(genre)
      genre_list.append(genre)
    genres = '"{%s}"' % ', '.join(genre_list)
  return genres

def generate_similar_artists_map(similar_artists):
  similar_artists_list = []
  if similar_artists:
    similar_artists = similar_artists.split('|')
    similar_artists = '"{%s}"' % ', '.join(similar_artists)
  return similar_artists

def wrap_name(name):
  return '"%s"' % name

def create_songs_ablums_by_artist_csv(line, data_to_write, write_file_path, limit, eof, artist_id_index, album_year_index, album_name_index, song_name_index, song_id_index, album_id_index, artist_genres_index, artist_name_index, similar_artists_index, song_duration_index):
  read_line_parts = line.split(',')
  # artist_id, album_year, album_name, song_name, song_id, album_id, artist_genres, artist_name, similar_artists, song_duration
  write_line_parts = []
  # artist_id
  write_line_parts.append(read_line_parts[artist_id_index])
  # album_year
  write_line_parts.append(read_line_parts[album_year_index])
  # album_name
  write_line_parts.append(wrap_name(read_line_parts[album_name_index]))
  # song_name
  write_line_parts.append(wrap_name(read_line_parts[song_name_index]))
  # song_id
  write_line_parts.append(read_line_parts[song_id_index])
  # album_id
  write_line_parts.append(read_line_parts[album_id_index])
  # artist_genres
  artist_genres = generate_genres_list(read_line_parts[artist_genres_index])
  write_line_parts.append(artist_genres)
  # artist_name
  write_line_parts.append(wrap_name(read_line_parts[artist_name_index]))
  # similar_artists
  similar_artists = generate_similar_artists_map(read_line_parts[similar_artists_index])
  write_line_parts.append(similar_artists)
  # song_duration
  write_line_parts.append(read_line_parts[song_duration_index])
  # build line
  # line = '|'.join(write_line_parts)
  line = ','.join(write_line_parts)
  skip = False
  if data_to_write and line == data_to_write[-1]:
    skip = True
  else:
    data_to_write.append(line)
    if len(data_to_write) == limit:
      skip = True
  if (len(data_to_write) >= limit and not skip) or eof:
    data_to_write = write_data(data_to_write, write_file_path, limit)

  return data_to_write

def create_featured_songs_csv(line, data_to_write, write_file_path, limit, eof, song_hotness_index, song_id_index, album_id_index, album_name_index, album_year_index, artist_id_index, artist_name_index, song_duration_index, song_name_index):
  read_line_parts = line.split(',')
  # hotness_bucket, content_type, song_hotness, song_id, album_id, album_name, album_year, artist_id, artist_name, song_duration, song_name
  write_line_parts = []
  # hotness_bucket
  song_hotness = read_line_parts[song_hotness_index]
  if song_hotness:
    write_line_parts.append('HOTNESS {}{}'.format(song_hotness[0], song_hotness[2]))
  else:
    write_line_parts.append('HOTNESS 00')
  # content_type
  write_line_parts.append('SONG')
  # song_hotness
  if song_hotness:
    write_line_parts.append(song_hotness)
  else:
    write_line_parts.append(format_float('0'))
  # song_id
  write_line_parts.append(read_line_parts[song_id_index])
  # album_id
  write_line_parts.append(read_line_parts[album_id_index])
  # album_name
  write_line_parts.append(wrap_name(read_line_parts[album_name_index]))
  # album_year
  write_line_parts.append(read_line_parts[album_year_index])
  # artist_id
  write_line_parts.append(read_line_parts[artist_id_index])
  # artist_name
  write_line_parts.append(wrap_name(read_line_parts[artist_name_index]))
  # song_duration
  write_line_parts.append(read_line_parts[song_duration_index])
  # song_name
  write_line_parts.append(wrap_name(read_line_parts[song_name_index]))
  # build line
  line = ','.join(write_line_parts)
  skip = False
  if data_to_write and line == data_to_write[-1]:
    skip = True
  else:
    data_to_write.append(line)
    if len(data_to_write) == limit:
      skip = True
  if (len(data_to_write) >= limit and not skip) or eof:
    data_to_write = write_data(data_to_write, write_file_path, limit)

  return data_to_write

def create_featured_albums_csv(line, data_to_write, write_file_path, limit, eof, album_hotness_index, album_id_index, album_name_index, album_year_index, artist_id_index, artist_name_index):
  read_line_parts = line.split(',')
  # hotness_bucket, content_type, album_hotness, album_id, album_name, album_year, artist_id, artist_name
  write_line_parts = []
  # hotness_bucket
  album_hotness = read_line_parts[album_hotness_index]
  if album_hotness:
    write_line_parts.append('HOTNESS {}{}'.format(album_hotness[0], album_hotness[2]))
  else:
    # write_line_parts.append("'HOTNESS 00'")
    write_line_parts.append('HOTNESS 00')
  # content_type
  write_line_parts.append('ALBUM')
  # album_hotness
  write_line_parts.append(album_hotness)
  # album_id
  write_line_parts.append(read_line_parts[album_id_index])
  # album_name
  write_line_parts.append(wrap_name(read_line_parts[album_name_index]))
  # album_year
  write_line_parts.append(read_line_parts[album_year_index])
  # artist_id
  write_line_parts.append(read_line_parts[artist_id_index])
  # artist_name
  write_line_parts.append(wrap_name(read_line_parts[artist_name_index]))
  # build line
  line = ','.join(write_line_parts)
  skip = False
  if data_to_write and line == data_to_write[-1]:
    skip = True
  else:
    data_to_write.append(line)
    if len(data_to_write) == limit:
      skip = True
  if (len(data_to_write) >= limit and not skip) or eof:
    data_to_write = write_data(data_to_write, write_file_path, limit)

  return data_to_write

def create_featured_artists_csv(line, data_to_write, write_file_path, limit, eof, artist_hotness_index, artist_id_index, artist_name_index):
  read_line_parts = line.split(',')
  # hotness_bucket, content_type, artist_hotness, artist_id, artist_name
  write_line_parts = []
  # hotness_bucket
  artist_hotness = read_line_parts[artist_hotness_index]
  if artist_hotness:
    write_line_parts.append('HOTNESS {}{}'.format(artist_hotness[0], artist_hotness[2]))
  else:
    write_line_parts.append('HOTNESS 00')
  # content_type
  write_line_parts.append('ARTIST')
  # artist_hotness
  write_line_parts.append(artist_hotness)
  # artist_id
  write_line_parts.append(read_line_parts[artist_id_index])
  # artist_name
  write_line_parts.append(wrap_name(read_line_parts[artist_name_index]))
  # build line
  line = ','.join(write_line_parts)
  skip = False
  if data_to_write and line == data_to_write[-1]:
    skip = True
  else:
    data_to_write.append(line)
    if len(data_to_write) == limit:
      skip = True
  if (len(data_to_write) >= limit and not skip) or eof:
    data_to_write = write_data(data_to_write, write_file_path, limit)

  return data_to_write

def create_artists_by_facet_csv(line, data_to_write, write_file_path, limit, eof, artist_genres_index, artist_name_index, artist_id_index):
  read_line_parts = line.split(',')
  # facet_name, content_type, artist_name, artist_id
  artist_id = read_line_parts[artist_id_index]
  artist_name = read_line_parts[artist_name_index]
  artist_genres = read_line_parts[artist_genres_index]
  lines_to_write = []
  if artist_genres:
    artist_genres = artist_genres.split('|')
    # print artist_genres
    for artist_genre in artist_genres:
      write_line_parts = []
      # facet_name
      write_line_parts.append('{}'.format(artist_genre.upper()))
      # content_type
      write_line_parts.append('ARTIST')
      # artist_name
      # write_line_parts.append(artist_name)
      write_line_parts.append(wrap_name(artist_name))
      # artist_id
      write_line_parts.append(artist_id)
      # build line
      lines_to_write.append(','.join(write_line_parts))
  skip = False
  if data_to_write and artist_id == data_to_write[-1].split(',')[3]:
    skip = True
  else:
    data_to_write.extend(lines_to_write)
    if len(data_to_write) == limit:
      skip = True
  if (len(data_to_write) >= limit and not skip) or eof:
    data_to_write = write_data(data_to_write, write_file_path, limit)

  return data_to_write

def create_albums_by_facet_csv(line, data_to_write, write_file_path, limit, eof, artist_genres_index, album_name_index, album_id_index, album_year_index, artist_id_index, artist_name_index):
  read_line_parts = line.split(',')
  # facet_name, content_type, album_name, album_id, album_year, artist_id, artist_name
  album_id = read_line_parts[album_id_index]
  album_name = read_line_parts[album_name_index]
  album_year = read_line_parts[album_year_index]
  artist_id = read_line_parts[artist_id_index]
  artist_name = read_line_parts[artist_name_index]
  artist_genres = read_line_parts[artist_genres_index]
  lines_to_write = []
  if artist_genres:
    artist_genres = artist_genres.split('|')
    # print artist_genres
    for artist_genre in artist_genres:
      write_line_parts = []
      # facet_name
      write_line_parts.append('{}'.format(artist_genre.upper()))
      # content_type
      write_line_parts.append('ALBUM')
      # album_name
      write_line_parts.append(wrap_name(album_name))
      # album_id
      write_line_parts.append(album_id)
      # album_year
      write_line_parts.append(album_year)
      # artist_id
      write_line_parts.append(artist_id)
      # artist_name
      write_line_parts.append(wrap_name(artist_name))
      # build line
      lines_to_write.append(','.join(write_line_parts))
  skip = False
  if data_to_write and album_id == data_to_write[-1].split(',')[3]:
    skip = True
  else:
    data_to_write.extend(lines_to_write)
    if len(data_to_write) == limit:
      skip = True
  if (len(data_to_write) >= limit and not skip) or eof:
    data_to_write = write_data(data_to_write, write_file_path, limit)

  return data_to_write

def create_songs_by_facet_csv(line, data_to_write, write_file_path, limit, eof, artist_genres_index, song_name_index, song_id_index, album_id_index, album_name_index, album_year_index, artist_id_index, artist_name_index, song_duration_index):
  read_line_parts = line.split(',')
  # facet_name, content_type, song_name, song_id, album_id, album_name, album_year, artist_id, artist_name, song_duration
  song_id = read_line_parts[song_id_index]
  song_name = read_line_parts[song_name_index]
  song_duration = read_line_parts[song_duration_index]
  album_id = read_line_parts[album_id_index]
  album_name = read_line_parts[album_name_index]
  album_year = read_line_parts[album_year_index]
  artist_id = read_line_parts[artist_id_index]
  artist_name = read_line_parts[artist_name_index]
  artist_genres = read_line_parts[artist_genres_index]
  lines_to_write = []
  if artist_genres:
    artist_genres = artist_genres.split('|')
    # print artist_genres
    for artist_genre in artist_genres:
      write_line_parts = []
      # facet_name
      write_line_parts.append('{}'.format(artist_genre.upper()))
      # content_type
      write_line_parts.append('SONG')
      # song_name
      write_line_parts.append(wrap_name(song_name))
      # song_id
      write_line_parts.append(song_id)
      # album_id
      write_line_parts.append(album_id)
      # album_name
      write_line_parts.append(wrap_name(album_name))
      # album_year
      write_line_parts.append(album_year)
      # artist_id
      write_line_parts.append(artist_id)
      # artist_name
      write_line_parts.append(wrap_name(artist_name))
      # song_duration
      write_line_parts.append(song_duration)
      # build line
      lines_to_write.append(','.join(write_line_parts))
  skip = False
  if data_to_write and song_id == data_to_write[-1].split(',')[3]:
    skip = True
  else:
    data_to_write.extend(lines_to_write)
    if len(data_to_write) == limit:
      skip = True
  if (len(data_to_write) >= limit and not skip) or eof:
    data_to_write = write_data(data_to_write, write_file_path, limit)

  return data_to_write

#
# organize data
# possible operations:
#   extract_hdf5
#   replace_song_id
#   replace_artist_id
#   replace_similar_artists
#   replace_album_id
#   create_songs_ablums_by_artist_csv
#   create_featured_songs_csv
#   create_featured_albums_csv
#   create_featured_artists_csv
#   create_artists_by_facet_csv
#   create_albums_by_facet_csv
#   create_songs_by_facet_csv
#
def read_write_data(read_file_path, remove, write_file_path, mode, operation, args, limit):
  data_to_write = []
  clear = open(write_file_path, mode)
  clear.close()
  with open(read_file_path) as read_file:
    line = read_file.readline().rstrip()
    next_line = read_file.readline().rstrip()
    eof = False
    while line:
      eof = False if next_line else True
      data_to_write = operation(line, data_to_write, write_file_path, limit, eof, *args)
      line = next_line
      if not eof:
        next_line = read_file.readline().rstrip()
  if remove:
    os.remove(read_file_path)

#
# mergesort
#
def create_directory(directory):
  if not os.path.exists(directory):
      os.makedirs(directory)

def create_filename(num):
  return '{}.txt'.format(str(num).zfill(5))

def sort_lines(lines, field, desc):
  return sorted(lines, key=lambda x: x.split(',')[field], reverse=desc)

def distribute_data(read_file_path, remove, directory, limit, field, desc):
  create_directory(directory)
  with open(read_file_path) as read_file:
    file_count = 0
    lines = []
    line = read_file.readline()
    next_line = read_file.readline()
    eof = False
    while line:
      eof = False if next_line else True
      lines.append(line)
      if len(lines) == limit or eof:
        file_count += 1
        filename = create_filename(file_count)
        with open(os.path.join(directory, filename), 'w') as target:
          for line in sort_lines(lines, field, desc):
            target.write(line)
        lines = []
      line = next_line
      next_line = read_file.readline()
  if remove:
    os.remove(read_file_path)

def merge_sort_data(read_file_path, remove, write_file_path, directory, limit, field, desc):
  distribute_data(read_file_path, remove, directory, limit, field, desc)

  files = os.listdir(directory)
  files_to_merge = []
  for f in files:
    files_to_merge.append(os.path.join(directory, f))
  num_files = len(files_to_merge)
  filename_count = num_files
  while num_files > 1:
    read_filename_one = files_to_merge.pop(0)
    read_filename_two = files_to_merge.pop(0)

    read_one = open(read_filename_one)
    read_two = open(read_filename_two)
    
    filename_count += 1
    filename = create_filename(filename_count)
    write_filename = os.path.join(directory, filename)
    write_one = open(write_filename, 'w')

    line_from_one = read_one.readline()
    line_from_two = read_two.readline()
    lines = []
    while line_from_one and line_from_two:
      lines.append(line_from_one)
      lines.append(line_from_two)
      lines = sort_lines(lines, field, desc)
      line = lines.pop(0)
      write_one.write(line)
      if line_from_one == line:
        line_from_one = read_one.readline()
      else:
        line_from_two = read_two.readline()
      lines = []
    if line_from_one == '':
      while line_from_two:
        lines.append(line_from_two)
        line_from_two = read_two.readline()
      lines = sort_lines(lines, field, desc)
      for line in lines:
        write_one.write(line)
    else:
      while line_from_one:
        lines.append(line_from_one)
        line_from_one = read_one.readline()
      lines = sort_lines(lines, field, desc)
      for line in lines:
        write_one.write(line)
    files_to_merge.append(write_filename)
    print files_to_merge
    num_files = len(files_to_merge)
    read_one.close()
    read_two.close()
    write_one.close()
    os.remove(read_filename_one)
    os.remove(read_filename_two)
  os.rename(files_to_merge[0], write_file_path)
  os.rmdir(directory)

start_time = time.time()

#
# ORGANIZE
#
limit = 1250 # TODO: limit is for mergesort evenly divisible whole number 1250 for 10,000, 15625 for 1,000,000, should be programmatic
remove_files = True
create_hdf5_files_list('{PATH TO MillionSongSubset/data}', 'zz01_hdf5_files_list.txt') # TODO: should be argument for script
read_write_data('zz01_hdf5_files_list.txt', remove_files, 'zz02_raw_data.txt', 'w', extract_hdf5, (), limit)

merge_sort_data('zz02_raw_data.txt', remove_files, 'zz03_data_sorted_by_song_id.txt', 'zz_data', limit, 0, False)
read_write_data('zz03_data_sorted_by_song_id.txt', remove_files, 'zz04_replaced_song_id.txt', 'w', replace_song_id, (0,), limit)
merge_sort_data('zz04_replaced_song_id.txt', remove_files, 'zz05_data_sorted_by_artist_id.txt', 'zz_data', limit, 8, False)
artist_ref_file = open('zz07_artist_old_id_new_id.txt', 'w')
artist_ref_file.close()
read_write_data('zz05_data_sorted_by_artist_id.txt', remove_files, 'zz06_replaced_artist_id.txt', 'w', replace_artist_id, (8, 9, 10, 11, 'zz07_artist_old_id_new_id.txt'), limit)
lookup = {}
with open('zz07_artist_old_id_new_id.txt') as artist_ref_file:
  line = artist_ref_file.readline().rstrip()
  while line:
    line_parts = line.split(',')
    lookup[line_parts[0]] = line_parts[1]
    line = artist_ref_file.readline().rstrip()
read_write_data('zz06_replaced_artist_id.txt', remove_files, 'zz08_replaced_similar_artists.txt', 'w', replace_similar_artists, (12, lookup), limit)
merge_sort_data('zz08_replaced_similar_artists.txt', remove_files, 'zz09_data_sorted_by_album_id.txt', 'zz_data', limit, 4, False)
read_write_data('zz09_data_sorted_by_album_id.txt', remove_files, 'zz10_replaced_album_id.txt', 'w', replace_album_id, (4, 3, 7, 6), limit)

#
# CQL
#
remove_files_query = False
merge_sort_data('zz10_replaced_album_id.txt', remove_files, 'zz11_data_sorted_by_artist_id.txt', 'zz_data', limit, 8, False)
# Q10
with open('Q10_songs_ablums_by_artist.csv', 'w') as songs_ablums_by_artist:
  songs_ablums_by_artist.write('artist_id|album_year|album_name|song_name|song_id|album_id|artist_genres|artist_name|similar_artists|song_duration\n')
read_write_data('zz11_data_sorted_by_artist_id.txt', remove_files_query, 'Q10_songs_ablums_by_artist.csv', 'a', create_songs_ablums_by_artist_csv, (8, 6, 5, 1, 0, 4, 11, 9, 12, 2), limit)
# Q8
with open('Q08_featured_artists.csv', 'w') as featured_artists:
  featured_artists.write('hotness_bucket|content_type|artist_hotness|artist_id|artist_name\n')
read_write_data('zz11_data_sorted_by_artist_id.txt', remove_files_query, 'Q08_featured_artists.csv', 'a', create_featured_artists_csv, (10, 8, 9), limit)
# Q9
with open('Q09_artists_by_facet.csv', 'w') as artists_by_facet:
  artists_by_facet.write('facet_name|content_type|artist_name|artist_id\n')
read_write_data('zz11_data_sorted_by_artist_id.txt', remove_files_query, 'Q09_artists_by_facet.csv', 'a', create_artists_by_facet_csv, (11, 9, 8), limit)

merge_sort_data('zz11_data_sorted_by_artist_id.txt', remove_files, 'zz12_data_sorted_by_song_id.txt', 'zz_data', limit, 0, False)
# Q4
with open('Q04_featured_songs.csv', 'w') as featured_songs:
  featured_songs.write('hotness_bucket|content_type|song_hotness|song_id|album_id|album_name|album_year|artist_id|artist_name|song_duration|song_name\n')
read_write_data('zz12_data_sorted_by_song_id.txt', remove_files_query, 'Q04_featured_songs.csv', 'a', create_featured_songs_csv, (3, 0, 4, 5, 6, 8, 9, 2, 1), limit)
# Q5
with open('Q05_songs_by_facet.csv', 'w') as artists_by_facet:
  artists_by_facet.write('facet_name|content_type|song_name|song_id|album_id|album_name|album_year|artist_id|artist_name|song_duration\n')
read_write_data('zz12_data_sorted_by_song_id.txt', remove_files_query, 'Q05_songs_by_facet.csv', 'a', create_songs_by_facet_csv, (11, 1, 0, 4, 5, 6, 8, 9, 2), limit)

merge_sort_data('zz12_data_sorted_by_song_id.txt', remove_files, 'zz13_data_sorted_by_album_id.txt', 'zz_data', limit, 4, False)
# Q6
with open('Q06_featured_albums.csv', 'w') as featured_albums:
  featured_albums.write('hotness_bucket|content_type|album_hotness|album_id|album_name|album_year|artist_id|artist_name\n')
read_write_data('zz13_data_sorted_by_album_id.txt', remove_files_query, 'Q06_featured_albums.csv', 'a', create_featured_albums_csv, (7, 4, 5, 6, 8, 9), limit)
#Q7
with open('Q07_albums_by_facet.csv', 'w') as albums_by_facet:
  albums_by_facet.write('facet_name|content_type|album_name|album_id|album_year|artist_id|artist_name\n')
read_write_data('zz13_data_sorted_by_album_id.txt', remove_files, 'Q07_albums_by_facet.csv', 'a', create_albums_by_facet_csv, (11, 5, 4, 6, 8, 9), limit)

end_time = time.time()
duration = int(end_time - start_time)
minutes = duration / 60
seconds = duration % 60
print str(minutes) + ':' + str(seconds)
