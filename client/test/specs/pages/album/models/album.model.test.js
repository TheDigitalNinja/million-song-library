/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import albumModule from 'pages/album/album.module.js';

describe('albumModel', () => {

  const ALBUM_LIST = { albums: ['album1', 'album2'] };
  const ALBUM_ID = 4;
  const FACET = '3';
  const doneFn = jasmine.createSpy('doneFn');
  const error = new Error('an error');

  let albumModel, albumStore, songStore, $log;

  beforeEach(() => {
    angular.mock.module(albumModule, ($provide) => {
      albumStore = jasmine.createSpyObj('albumStore', ['fetch', 'fetchAll']);
      songStore = jasmine.createSpyObj('songStore', ['fetch']);
      $log = jasmine.createSpyObj('$log', ['warn']);

      $provide.value('albumStore', albumStore);
      $provide.value('songStore', songStore);
      $provide.value('$log', $log);
    });

    inject((_albumModel_) => {
      albumModel = _albumModel_;
    });
  });

  it('should instantiate the model', () => {
    expect(albumModel).toBeDefined();
  });

  describe('getAlbum', () => {
    it('should get the album information', (done) => {
      (async () => {
        await albumModel.getAlbum(ALBUM_ID);
        expect(albumStore.fetch).toHaveBeenCalledWith(ALBUM_ID);
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        albumStore.fetch.and.throwError(error);

        await albumModel.getAlbum(ALBUM_ID);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('getAlbums', () => {
    it('should get the list of albums', (done) => {
      (async () => {
        await albumModel.getAlbums();
        expect(albumStore.fetchAll).toHaveBeenCalled();
        done();
      })();
    });

    it('should set the albums of the model', (done) => {
      (async () => {
        albumStore.fetchAll.and.returnValue(ALBUM_LIST);
        await albumModel.getAlbums();
        expect(albumModel.albums).toEqual(ALBUM_LIST.albums);
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        albumStore.fetchAll.and.throwError(error);

        await albumModel.getAlbums();
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('filterAlbums', () => {
    it('should filter the albums by genres', (done) => {
      (async () => {
        await albumModel.filterAlbums(FACET, doneFn);
        expect(albumStore.fetchAll).toHaveBeenCalledWith(FACET);
        done();
      })();
    });

    it('should call the done function', (done) => {
      (async () => {
        const albumList = { albums: ['anAlbum'] };
        albumStore.fetchAll.and.returnValue(albumList);

        await albumModel.filterAlbums(FACET, doneFn);
        expect(doneFn).toHaveBeenCalledWith(albumList.albums);
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        albumStore.fetchAll.and.throwError(error);

        await albumModel.filterAlbums(FACET, doneFn);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('getAlbumSongs', () => {
    const album = { songsList: ['aSongId'] };

    it('should get the album from the album store', (done) => {
      (async () => {
        await albumModel.getAlbumSongs(ALBUM_ID, doneFn);
        expect(albumStore.fetch).toHaveBeenCalledWith(ALBUM_ID);
        done();
      })();
    });

    it('should get the songs from the album songs list', (done) => {
      (async () => {
        albumStore.fetch.and.returnValue(album);
        await albumModel.getAlbumSongs(ALBUM_ID, doneFn);
        expect(songStore.fetch).toHaveBeenCalledWith('aSongId');
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        albumStore.fetch.and.throwError(error);

        await albumModel.getAlbumSongs(ALBUM_ID, doneFn);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });

    it('should set the songs as empty when the song store fails', (done) => {
      (async () => {
        songStore.fetch.and.throwError(error);
        albumStore.fetch.and.returnValue(album);

        await albumModel.getAlbumSongs(ALBUM_ID, doneFn);
        expect($log.warn).toHaveBeenCalledWith(error);
        expect(albumModel.songs).toEqual([]);
        done();
      })();
    });
  });
});
