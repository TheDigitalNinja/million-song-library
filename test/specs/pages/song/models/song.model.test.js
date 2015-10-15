/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import songModule from 'pages/song/song.module.js';

describe('songModel', () => {

  const SONG_ID = 4;

  let catalogStore, songModel, songStore;

  beforeEach(() => {
    angular.mock.module(songModule);
    inject(($injector) => {
      songModel = $injector.get('songModel');
      songStore = $injector.get('songStore');
      catalogStore = $injector.get('catalogStore');
    });
  });

  it('should instantiate the model', () => {
    expect(songModel).toBeDefined();
  });

  describe('getSong', () => {
    it('should get the song information', (done) => {
      (async () => {
        spyOn(songStore, 'fetch');
        await songModel.getSong(SONG_ID);
        done();
      })();
      expect(songStore.fetch).toHaveBeenCalledWith(SONG_ID);
    });
  });

  describe('getSongs', () => {
    it('should get the list of songs', (done) => {
      (async () => {
        spyOn(catalogStore, 'fetch');
        await (songModel.getSongs());
        done();
      })();
      expect(catalogStore.fetch).toHaveBeenCalled();
    });
  });

});
