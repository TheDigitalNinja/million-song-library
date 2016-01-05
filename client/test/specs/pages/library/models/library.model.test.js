/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryModule from 'pages/library/library.module.js';

describe('libraryModel', () => {
  const SONG_ID = 5;
  const ALBUM_ID = 4;
  const ARTIST_ID = 2;
  const TIMESTAMP = 'Thu Feb 05 00:31:49 CST 2015';

  let libraryModel, $log, myLibraryStore, toastr, $rootScope;

  beforeEach(() => {
    angular.mock.module(libraryModule, ($provide) => {
      $log = jasmine.createSpyObj('$log', ['info', 'error']);
      myLibraryStore = jasmine.createSpyObj('myLibraryStore', ['fetch', 'addSong', 'addAlbum', 'addArtist', 'removeSong', 'removeAlbum', 'removeArtist']);
      toastr = jasmine.createSpyObj('toastr', ['error', 'success']);

      $provide.value('$log', $log);
      $provide.value('myLibraryStore', myLibraryStore);
      $provide.value('toastr', toastr);
    });

    inject((_libraryModel_, _$rootScope_) => {
      libraryModel = _libraryModel_;
      $rootScope = _$rootScope_;
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

    it('should log the error', (done) => {
      (async () => {
        const error = new Error();
        myLibraryStore.fetch.and.throwError(error);
        await libraryModel.getLibrary();
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('addSongToLibrary', () => {
    it('should log a message with the SONG_ID', () => {
      libraryModel.addSongToLibrary(SONG_ID);
      expect(myLibraryStore.addSong).toHaveBeenCalledWith(SONG_ID);
    });

    it('should show the success message', (done) => {
      (async () => {
        myLibraryStore.addSong.and.returnValue({ message: 'success'});
        await libraryModel.addSongToLibrary(SONG_ID);
        expect(toastr.success).toHaveBeenCalledWith('Successfully added song to library');
        done();
      })();
    });

    it('should emit the song added to library event', (done) => {
      (async () => {
        spyOn($rootScope, '$emit');
        myLibraryStore.addSong.and.returnValue({ message: 'success'});
        await libraryModel.addSongToLibrary(SONG_ID);
        expect($rootScope.$emit).toHaveBeenCalledWith('addedToLibrary', 'Song');
        done();
      })();
    });

    it('should log the error', (done) => {
      (async () => {
        const error = new Error();
        myLibraryStore.addSong.and.throwError(error);
        await libraryModel.addSongToLibrary(SONG_ID);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

    it('should display the error message', (done) => {
      (async () => {
        myLibraryStore.addSong.and.returnValue({ message: 'fail'});
        await libraryModel.addSongToLibrary(SONG_ID);
        expect(toastr.error).toHaveBeenCalledWith('Unable to add song to library, please try again later');
        done();
      })();
    });
  });

  describe('addAlbumToLibrary', () => {
    it('should log a message with the ALBUM_ID', () => {
      libraryModel.addAlbumToLibrary(ALBUM_ID);
      expect(myLibraryStore.addAlbum).toHaveBeenCalledWith(ALBUM_ID);
    });

    it('should show the success message', (done) => {
      (async () => {
        myLibraryStore.addAlbum.and.returnValue({ message: 'success'});
        await libraryModel.addAlbumToLibrary(ALBUM_ID);
        expect(toastr.success).toHaveBeenCalledWith('Successfully added album to library');
        done();
      })();
    });

    it('should emit the album added to library event', (done) => {
      (async () => {
        spyOn($rootScope, '$emit');
        myLibraryStore.addAlbum.and.returnValue({ message: 'success'});
        await libraryModel.addAlbumToLibrary(ALBUM_ID);
        expect($rootScope.$emit).toHaveBeenCalledWith('addedToLibrary', 'Album');
        done();
      })();
    });

    it('should log the error', (done) => {
      (async () => {
        const error = new Error();
        myLibraryStore.addAlbum.and.throwError(error);
        await libraryModel.addAlbumToLibrary(ALBUM_ID);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

    it('should display the error message', (done) => {
      (async () => {
        myLibraryStore.addAlbum.and.returnValue({ message: 'fail'});
        await libraryModel.addAlbumToLibrary(ALBUM_ID);
        expect(toastr.error).toHaveBeenCalledWith('Unable to add album to library, please try again later');
        done();
      })();
    });
  });

  describe('addArtistToLibrary', () => {
    it('should log the a message with the ARTIST_ID', () => {
      libraryModel.addArtistToLibrary(ARTIST_ID);
      expect(myLibraryStore.addArtist).toHaveBeenCalledWith(ARTIST_ID);
    });

    it('should show the success message', (done) => {
      (async () => {
        myLibraryStore.addArtist.and.returnValue({ message: 'success'});
        await libraryModel.addArtistToLibrary(ARTIST_ID);
        expect(toastr.success).toHaveBeenCalledWith('Successfully added artist to library');
        done();
      })();
    });

    it('should emit the artist added to library event', (done) => {
      (async () => {
        spyOn($rootScope, '$emit');
        myLibraryStore.addArtist.and.returnValue({ message: 'success'});
        await libraryModel.addArtistToLibrary(ARTIST_ID);
        expect($rootScope.$emit).toHaveBeenCalledWith('addedToLibrary', 'Artist');
        done();
      })();
    });

    it('should log the error', (done) => {
      (async () => {
        const error = new Error();
        myLibraryStore.addArtist.and.throwError(error);
        await libraryModel.addArtistToLibrary(ARTIST_ID);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

    it('should display the error message', (done) => {
      (async () => {
        myLibraryStore.addArtist.and.returnValue({ message: 'fail'});
        await libraryModel.addArtistToLibrary(ARTIST_ID);
        expect(toastr.error).toHaveBeenCalledWith('Unable to add artist to library, please try again later');
        done();
      })();
    });
  });

  describe('removeSongFromLibrary', () => {
    it('should log a message with the SONG_ID', () => {
      libraryModel.removeSongFromLibrary(SONG_ID, TIMESTAMP);
      expect(myLibraryStore.removeSong).toHaveBeenCalledWith(SONG_ID, TIMESTAMP);
    });

    it('should show the success message', (done) => {
      (async () => {
        myLibraryStore.removeSong.and.returnValue({ message: 'success'});
        await libraryModel.removeSongFromLibrary(SONG_ID);
        expect(toastr.success).toHaveBeenCalledWith('Successfully removed song');
        done();
      })();
    });

    it('should emit the song deleted from library event', (done) => {
      (async () => {
        spyOn($rootScope, '$emit');
        myLibraryStore.removeSong.and.returnValue({ message: 'success'});
        await libraryModel.removeSongFromLibrary(SONG_ID);
        expect($rootScope.$emit).toHaveBeenCalledWith('deletedFromLibrary', 'Song');
        done();
      })();
    });

    it('should log the error', (done) => {
      (async () => {
        const error = new Error();
        myLibraryStore.removeSong.and.throwError(error);
        await libraryModel.removeSongFromLibrary(SONG_ID);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

    it('should display the error message', (done) => {
      (async () => {
        myLibraryStore.removeSong.and.returnValue({ message: 'fail'});
        await libraryModel.removeSongFromLibrary(SONG_ID);
        expect(toastr.error).toHaveBeenCalledWith('Unable to delete song, please try again later');
        done();
      })();
    });
  });

  describe('removeAlbumToLibrary', () => {
    it('should log a message with the ALBUM_ID', () => {
      libraryModel.removeAlbumFromLibrary(ALBUM_ID, TIMESTAMP);
      expect(myLibraryStore.removeAlbum).toHaveBeenCalledWith(ALBUM_ID, TIMESTAMP);
    });

    it('should show the success message', (done) => {
      (async () => {
        myLibraryStore.removeAlbum.and.returnValue({ message: 'success'});
        await libraryModel.removeAlbumFromLibrary(ALBUM_ID);
        expect(toastr.success).toHaveBeenCalledWith('Successfully removed album');
        done();
      })();
    });

    it('should emit the album deleted from library event', (done) => {
      (async () => {
        spyOn($rootScope, '$emit');
        myLibraryStore.removeAlbum.and.returnValue({ message: 'success'});
        await libraryModel.removeAlbumFromLibrary(ALBUM_ID);
        expect($rootScope.$emit).toHaveBeenCalledWith('deletedFromLibrary', 'Album');
        done();
      })();
    });

    it('should log the error', (done) => {
      (async () => {
        const error = new Error();
        myLibraryStore.removeAlbum.and.throwError(error);
        await libraryModel.removeAlbumFromLibrary(ALBUM_ID);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

    it('should display the error message', (done) => {
      (async () => {
        myLibraryStore.removeAlbum.and.returnValue({ message: 'fail'});
        await libraryModel.removeAlbumFromLibrary(ALBUM_ID);
        expect(toastr.error).toHaveBeenCalledWith('Unable to delete album, please try again later');
        done();
      })();
    });
  });

  describe('removeArtistFromLibrary', () => {
    it('should log the a message with the ARTIST_ID', () => {
      libraryModel.removeArtistFromLibrary(ARTIST_ID, TIMESTAMP);
      expect(myLibraryStore.removeArtist).toHaveBeenCalledWith(ARTIST_ID, TIMESTAMP);
    });

    it('should show the success message', (done) => {
      (async () => {
        myLibraryStore.removeArtist.and.returnValue({ message: 'success'});
        await libraryModel.removeArtistFromLibrary(ARTIST_ID);
        expect(toastr.success).toHaveBeenCalledWith('Successfully removed artist');
        done();
      })();
    });

    it('should emit the artist deleted from library event', (done) => {
      (async () => {
        spyOn($rootScope, '$emit');
        myLibraryStore.removeArtist.and.returnValue({ message: 'success'});
        await libraryModel.removeArtistFromLibrary(ARTIST_ID);
        expect($rootScope.$emit).toHaveBeenCalledWith('deletedFromLibrary', 'Artist');
        done();
      })();
    });

    it('should log the error', (done) => {
      (async () => {
        const error = new Error();
        myLibraryStore.removeArtist.and.throwError(error);
        await libraryModel.removeArtistFromLibrary(ARTIST_ID);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

    it('should display the error message', (done) => {
      (async () => {
        myLibraryStore.removeArtist.and.returnValue({ message: 'fail'});
        await libraryModel.removeArtistFromLibrary(ARTIST_ID);
        expect(toastr.error).toHaveBeenCalledWith('Unable to delete artist, please try again later');
        done();
      })();
    });
  });

});
