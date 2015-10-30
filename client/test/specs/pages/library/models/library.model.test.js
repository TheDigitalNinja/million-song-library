/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryModule from 'pages/library/library.module.js';

describe('libraryModel', () => {
  const SONG_ID = 5;
  const ALBUM_ID = 4;
  const ARTIST_ID = 2;

  let libraryModel, $log;

  beforeEach(() => {
    angular.mock.module(libraryModule, ($provide) => {
      $log = jasmine.createSpyObj('$log', ['info']);

      $provide.value('$log', $log);
    });

    inject((_libraryModel_) => {
      libraryModel = _libraryModel_;
    });
  });

  it('should instantiate the model', () => {
    expect(libraryModel).toBeDefined();
  });

  describe('addSongToLibrary', () => {
    it('should log a message with the SONG_ID', () => {
      libraryModel.addSongToLibrary(SONG_ID);
      expect($log.info).toHaveBeenCalledWith(`Adding song ${SONG_ID} to library`);
    });
  });

  describe('addAlbumToLibrary', () => {
    it('should log a message with the ALBUM_ID', () => {
      libraryModel.addAlbumToLibrary(ALBUM_ID);
      expect($log.info).toHaveBeenCalledWith(`Adding album ${ALBUM_ID} to library`);
    });
  });

  describe('addArtistToLibrary', () => {
    it('should log the a message with the ARTIST_ID', () => {
      libraryModel.addArtistToLibrary(ARTIST_ID);
      expect($log.info).toHaveBeenCalledWith(`Adding artist ${ARTIST_ID} to library`);
    });
  });

});
