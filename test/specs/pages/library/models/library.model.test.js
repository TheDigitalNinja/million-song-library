/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryModule from 'pages/library/library.module.js';

describe('libraryModel', () => {

  const SONG_ID = 5;
  const ALBUM_ID = 4;
  const ARTIST_ID = 2;

  let libraryModel, myLibraryStore, $log;

  beforeEach(() => {
    angular.mock.module(libraryModule);
    inject(() => {
      let $injector = angular.injector(['msl.library']);
      libraryModel = $injector.get('libraryModel');
      myLibraryStore = $injector.get('myLibraryStore');
      $log = $injector.get('$log');
    });
  });

  it('should instantiate the model', () => {
    expect(libraryModel).toBeDefined();
  });

  describe('addSongToLibrary', () => {
    it('should call myLibraryStore.addSong with SONG_ID', (done) => {
      (async () => {
        spyOn(myLibraryStore, 'addSong');
        await libraryModel.addSongToLibrary(SONG_ID);
        done();
      })();
      expect(myLibraryStore.addSong).toHaveBeenCalledWith(SONG_ID);
    });
  });

  describe('addAlbumToLibrary', () => {
    it('should log a message with the ALBUM_ID', () => {
      spyOn($log, 'info');
      libraryModel.addAlbumToLibrary(ALBUM_ID);
      expect($log.info).toHaveBeenCalledWith(`Adding album ${ALBUM_ID} to library`);
    });
  });

  describe('addArtistToLibrary', () => {
    it('should log the a message with the ARTIST_ID', () => {
      spyOn($log, 'info');
      libraryModel.addArtistToLibrary(ARTIST_ID);
      expect($log.info).toHaveBeenCalledWith(`Adding artist ${ARTIST_ID} to library`);
    });
  });

});
