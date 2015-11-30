/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import songModule from 'pages/song/song.module.js';

describe('songModel', () => {

  const SONG_ID = 4;
  const error = new Error('an error');

  let songModel, songStore, $log;

  beforeEach(() => {
    angular.mock.module(songModule, ($provide) => {
      songStore = jasmine.createSpyObj('songStore', ['fetch', 'fetchAll']);
      $log = jasmine.createSpyObj('$log', ['warn']);

      $provide.value('songStore', songStore);
      $provide.value('$log', $log);
    });

    inject((_songModel_) => {
      songModel = _songModel_;
    });
  });

  it('should instantiate the model', () => {
    expect(songModel).toBeDefined();
  });

  describe('getSong', () => {
    it('should fetch the song from the songStore', (done) => {
      (async () => {
        await songModel.getSong(SONG_ID);
        expect(songStore.fetch).toHaveBeenCalledWith(SONG_ID);
        done();
      })();
    });

    it('should call the done callback with the returned song', (done) => {
      (async () => {
        const doneFn = jasmine.createSpy('doneFn');
        const song = jasmine.createSpy('a_song');
        songStore.fetch.and.returnValue(song);
        await songModel.getSong(SONG_ID, doneFn);
        expect(doneFn).toHaveBeenCalledWith(song);
        done();
      })();
    });

    it('should log a warn when a error is thrown', (done) => {
      (async () => {
        songStore.fetch.and.throwError(error);
        await songModel.getSong(SONG_ID);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('getSongs', () => {
    it('should get the list of songs', (done) => {
      (async () => {
        const songsList = { songs: ['song'] };
        songStore.fetchAll.and.returnValue(songsList);
        await (songModel.getSongs());
        expect(songModel.songs).toEqual(songsList.songs);
        done();
      })();
    });

    it('should log a warn when a error is thrown', (done) => {
      (async () => {
        songStore.fetchAll.and.throwError(error);
        await (songModel.getSongs());
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('filterSongs', () => {
    const facets = '4';

    it('should fetch the songs with the given rating and genre', (done) => {
      (async () => {
        const params = facets;
        await songModel.filterSongs(facets);
        expect(songStore.fetchAll).toHaveBeenCalledWith(params);
        done();
      })();
    });

    it('should call the done callback with the songs', (done) => {
      (async () => {
        const songsList = { songs: ['song'] };
        const doneFn = jasmine.createSpy('doneFn');
        songStore.fetchAll.and.returnValue(songsList);
        await songModel.filterSongs(facets, doneFn);
        expect(doneFn).toHaveBeenCalledWith(songsList.songs);
        done();
      })();
    });

    it('should log a warn when a error is thrown', (done) => {
      (async () => {
        const params = facets;
        songStore.fetchAll.and.throwError(error);
        await songModel.filterSongs(facets);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });
});
