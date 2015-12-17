/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryModule from 'pages/library/library.module.js';

describe('libraryModel', () => {
  const SONG_ID = 5;
  const ALBUM_ID = 4;
  const ARTIST_ID = 2;
  const TIMESTAMP = 'Thu Feb 05 00:31:49 CST 2015';

  let libraryModel, $log, myLibraryStore;

  beforeEach(() => {
    angular.mock.module(libraryModule, ($provide) => {
      $log = jasmine.createSpyObj('$log', ['info']);
      myLibraryStore = jasmine.createSpyObj('myLibraryStore', ['fetch', 'addSong', 'addAlbum', 'addArtist', 'removeSong', 'removeAlbum', 'removeArtist']);

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
      expect(myLibraryStore.addArtist).toHaveBeenCalledWith(ARTIST_ID);
    });
  });

  describe('removeSongFromLibrary', () => {
    it('should log a message with the SONG_ID', () => {
      libraryModel.removeSongFromLibrary(SONG_ID, TIMESTAMP);
      expect(myLibraryStore.removeSong).toHaveBeenCalledWith(SONG_ID, TIMESTAMP);
    });
  });

  describe('removeAlbumToLibrary', () => {
    it('should log a message with the ALBUM_ID', () => {
      libraryModel.removeAlbumFromLibrary(ALBUM_ID, TIMESTAMP);
      expect(myLibraryStore.removeAlbum).toHaveBeenCalledWith(ALBUM_ID, TIMESTAMP);
    });
  });

  describe('removeArtistFromLibrary', () => {
    it('should log the a message with the ARTIST_ID', () => {
      libraryModel.removeArtistFromLibrary(ARTIST_ID, TIMESTAMP);
      expect(myLibraryStore.removeArtist).toHaveBeenCalledWith(ARTIST_ID, TIMESTAMP);
    });
  });

});
