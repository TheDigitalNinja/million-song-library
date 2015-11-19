/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryModule from 'pages/library/library.module.js';

describe('libraryModel', () => {
  const SONG_ID = 5;
  const ALBUM_ID = 4;
  const ARTIST_ID = 2;

  let libraryModel, $log, myLibraryStore;

  beforeEach(() => {
    angular.mock.module(libraryModule, ($provide) => {
      $log = jasmine.createSpyObj('$log', ['info']);
      myLibraryStore = jasmine.createSpyObj('myLibraryStore', ['fetch', 'addSong', 'addAlbum']);

      $provide.value('$log', $log);
      $provide.value('myLibraryStore', myLibraryStore);
    });

    inject((_libraryModel_) => {
      libraryModel = _libraryModel_;
    });
  });

  it('should instantiate the model', () => {
    expect(libraryModel).toBeDefined();
  });

  describe('getLibrary', () => {
    it('should fetch my library', (done) => {
      (async () => {
        await libraryModel.getLibrary();
        expect(myLibraryStore.fetch).toHaveBeenCalled();
        done();
      })();
    });

    it('should return my library fetch result', (done) => {
      (async () => {
        const expectedResponse = 'a_response';
        myLibraryStore.fetch.and.returnValue(expectedResponse);
        const response = await libraryModel.getLibrary();
        expect(response).toBe(expectedResponse);
        done();
      })();
    });
  });

  describe('addSongToLibrary', () => {
    it('should log a message with the SONG_ID', () => {
      libraryModel.addSongToLibrary(SONG_ID);
      expect(myLibraryStore.addSong).toHaveBeenCalledWith(SONG_ID);
    });
  });

  describe('addAlbumToLibrary', () => {
    it('should log a message with the ALBUM_ID', () => {
      libraryModel.addAlbumToLibrary(ALBUM_ID);
      expect(myLibraryStore.addAlbum).toHaveBeenCalledWith(ALBUM_ID);
    });
  });

  describe('addArtistToLibrary', () => {
    it('should log the a message with the ARTIST_ID', () => {
      libraryModel.addArtistToLibrary(ARTIST_ID);
      expect($log.info).toHaveBeenCalledWith(`Adding artist ${ARTIST_ID} to library`);
    });
  });

});
