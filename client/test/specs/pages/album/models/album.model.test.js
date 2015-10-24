/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import albumModule from 'pages/album/album.module.js';

describe('albumModel', () => {

  const ALBUMS_LIST = ['album1', 'album2'];
  const ALBUM_ID = 4;

  let albumModel, albumStore;

  beforeEach(() => {
    angular.mock.module(albumModule);
    inject(($injector) => {
      albumModel = $injector.get('albumModel');
      albumStore = $injector.get('albumStore');
    });
  });

  it('should instantiate the model', () => {
    expect(albumModel).toBeDefined();
  });

  describe('getAlbum', () => {
    it('should get the album information', (done) => {
      (async () => {
        spyOn(albumStore, 'fetch');
        await albumModel.getAlbum(ALBUM_ID);
        done();
      })();
      expect(albumStore.fetch).toHaveBeenCalledWith(ALBUM_ID);
    });
  });

  describe('getAlbums', () => {
    it('should get the list of albums', (done) => {
      (async () => {
        spyOn(albumStore, 'fetchAll');
        await albumModel.getAlbums();
        done();
      })();
      expect(albumStore.fetchAll).toHaveBeenCalled();
    });
  });

});
