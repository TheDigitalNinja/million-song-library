import time
import sys
import os
import multiprocessing
from multiprocessing import Process, Manager
import hdf5_getters
import string

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

def extract(reader_queue, writer_queue):
  while True:
    file_path = reader_queue.get()
    reader_queue.task_done()
    if (file_path == 'STOP'):
      writer_queue.put('STOP')
      return
    h5 = hdf5_getters.open_h5_file_read(file_path)
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
    writer_queue.put(','.join(song_data))

def read(read_file_path, reader_queue, num_workers):
  with open(read_file_path) as read_file:
    file_path = read_file.readline().rstrip()
    while file_path:
      reader_queue.put(file_path)
      file_path = read_file.readline().rstrip()
    for w in xrange(num_workers):
      reader_queue.put('STOP')
    return

def write(write_file_path, writer_queue, num_workers):
  with open(write_file_path, 'w') as write_file:
    i = 0
    while i < num_workers:
      line = writer_queue.get()
      if line == 'STOP':
        i += 1
      else:
        write_file.write(line + '\n')
      writer_queue.task_done()
    return

def main(argv):
  read_file_path = '01_h5_list.txt'
  create_hdf5_files_list(argv[0], read_file_path)

  print 'CPU COUNT ' + str(multiprocessing.cpu_count())
  num_workers = multiprocessing.cpu_count()
  write_file_path = '02_raw_data.txt'
  reader_manager = Manager()
  reader_queue = reader_manager.Queue(num_workers)
  writer_manager = Manager()
  writer_queue = writer_manager.Queue(num_workers)

  reader = Process(target=read, args=(read_file_path, reader_queue, num_workers))
  reader.daemon = True
  reader.start()
  writer = Process(target=write, args=(write_file_path, writer_queue, num_workers))
  writer.daemon = True
  writer.start()

  pool = []
  pool.append(writer)
  pool.append(reader)

  for i in xrange(num_workers):
    extractor = Process(target=extract, args=(reader_queue, writer_queue))
    extractor.daemon = True
    pool.append(extractor)
    extractor.start()

  for p in pool:
    p.join()

  return

if __name__ == "__main__":
  print 'START'
  start_time = time.time()
  main(sys.argv[1:])
  end_time = time.time()
  duration = int(end_time - start_time)
  minutesSeconds = '{:02d}:{:02d}'.format(duration / 60, duration % 60)
  print minutesSeconds
  print 'STOP'
  sys.exit(0)
