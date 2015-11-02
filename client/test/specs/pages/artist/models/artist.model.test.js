import artistModule from 'pages/artist/artist.module.js';

describe('artistModel', () => {
  const ARTIST_ID = 5;
  const FACET = '3';
  const doneFn = jasmine.createSpy('doneFn');
  const error = new Error('an error');

  let artistModel, albumStore, artistStore, songStore, $log;

  beforeEach(() => {
    angular.mock.module(artistModule, ($provide) => {
      albumStore = jasmine.createSpyObj('albumStore', ['fetch']);
      artistStore = jasmine.createSpyObj('artistStore', ['fetch', 'fetchAll']);
      songStore = jasmine.createSpyObj('songStore', ['fetch', 'fetchAll']);
      $log = jasmine.createSpyObj('$log', ['warn']);

      $provide.value('albumStore', albumStore);
      $provide.value('artistStore', artistStore);
      $provide.value('songStore', songStore);
      $provide.value('$log', $log);
    });

    inject((_artistModel_) => {
      artistModel = _artistModel_;
    });
  });

  it('should instantiate the model', () => {
    expect(artistModel).toBeDefined();
  });

  describe('getArtist', () => {
    it('should fetch the artist information', (done) => {
      (async () => {
        await artistModel.getArtist(ARTIST_ID);
        expect(artistStore.fetch).toHaveBeenCalledWith(ARTIST_ID);
        done();
      })();
    });

    it('should call the done function', (done) => {
      (async () => {
        const artist = 'an_artist';
        artistStore.fetch.and.returnValue(artist);
        await artistModel.getArtist('1', doneFn);
        expect(doneFn).toHaveBeenCalledWith(artist);
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        artistStore.fetch.and.throwError(error);
        await artistModel.getArtist(ARTIST_ID);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('getArtists', () => {
    it('should get the list of artists', (done) => {
      (async () => {
        await artistModel.getArtists();
        expect(artistStore.fetchAll).toHaveBeenCalled();
        done();
      })();
    });

    it('should set the artists of the model', (done) => {
      (async () => {
        const artistList = { artists: ['artist1', 'artist2'] };
        artistStore.fetchAll.and.returnValue(artistList);
        await artistModel.getArtists();
        expect(artistModel.artists).toEqual(artistList.artists);
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        artistStore.fetchAll.and.throwError(error);
        await artistModel.getArtists();
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('getArtistAlbums', () => {
    it('should fetch the albums', (done) => {
      (async () => {
        const artistAlbumIds = ['1', '2'];
        await artistModel.getArtistAlbums(artistAlbumIds);
        expect(albumStore.fetch).toHaveBeenCalledWith('1');
        expect(albumStore.fetch).toHaveBeenCalledWith('2');
        done();
      })();
    });

    it('should call the done function', (done) => {
      (async () => {
        const album = 'anAlbum';
        albumStore.fetch.and.returnValue(album);
        await artistModel.getArtistAlbums(['1'], doneFn);
        expect(doneFn).toHaveBeenCalledWith([album]);
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        const artistAlbumIds = ['1', '2'];
        albumStore.fetch.and.throwError(error);
        await artistModel.getArtistAlbums(artistAlbumIds);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('getSimilarArtists', () => {
    it('should fetch the artist', (done) => {
      (async () => {
        const similarArtistIds = ['1', '2'];
        artistStore.fetch.and.returnValue({ similarArtistsList: similarArtistIds });
        await artistModel.getSimilarArtists(ARTIST_ID);
        expect(artistStore.fetch).toHaveBeenCalledWith(ARTIST_ID);
        done();
      })();
    });

    it('should fetch the similar artists', (done) => {
      (async () => {
        const similarArtistIds = ['3', '4'];
        artistStore.fetch.and.returnValue({ similarArtistsList: similarArtistIds });
        await artistModel.getSimilarArtists(ARTIST_ID);
        expect(artistStore.fetch).toHaveBeenCalledWith('3');
        expect(artistStore.fetch).toHaveBeenCalledWith('4');
        done();
      })();
    });

    it('should call the done function with the list of artists', (done) => {
      (async () => {
        const artistList = ['artist 1'];

        artistStore.fetch.and.returnValue({ similarArtistsList: ['1']});
        spyOn(artistModel, 'getArtistsById');
        artistModel.getArtistsById.and.callFake((_, cb) => cb(artistList) );

        await artistModel.getSimilarArtists(ARTIST_ID, doneFn);
        expect(doneFn).toHaveBeenCalledWith(artistList);
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        artistStore.fetch.and.throwError(error);
        await artistModel.getSimilarArtists(ARTIST_ID);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('getArtistsById', () => {
    it('should call the done function', (done) => {
      (async () => {
        const artist = 'anArtist';
        artistStore.fetch.and.returnValue(artist);
        await artistModel.getArtistsById(['1'], doneFn);
        expect(doneFn).toHaveBeenCalledWith([artist]);
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        artistStore.fetch.and.throwError(error);
        await artistModel.getArtistsById(['1'], doneFn);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('filterArtists', () => {
    it('should fetch the albums', (done) => {
      (async () => {
        await artistModel.filterArtists(FACET);
        expect(artistStore.fetchAll).toHaveBeenCalledWith('3');
        done();
      })();
    });

    it('should call the done function', (done) => {
      (async () => {
        const artistList = { artists: ['anArtist'] };
        artistStore.fetchAll.and.returnValue(artistList);
        await artistModel.filterArtists(FACET, doneFn);
        expect(doneFn).toHaveBeenCalledWith(artistList.artists);
        done();
      })();
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        artistStore.fetchAll.and.throwError(error);
        await artistModel.filterArtists(FACET, doneFn);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('getArtistSongs', () => {
    const artist = { songsList: ['1', '2'] };

    it('should fetch the songs', (done) => {
      (async () => {
        await artistModel.getArtistSongs(artist);
        expect(songStore.fetch).toHaveBeenCalledWith('1');
        expect(songStore.fetch).toHaveBeenCalledWith('2');
        done();
      })();
    });

    it('should call the done function', (done) => {
      (async () => {
        const song = 'a_song';
        songStore.fetch.and.returnValue(song);
        await artistModel.getArtistSongs(artist, doneFn);
        expect(doneFn).toHaveBeenCalledWith([song, song]);
        done();
      })();
    });

    it('should log a warn when an error is thrown', (done) => {
      (async () => {
        songStore.fetch.and.throwError(error);
        await artistModel.getArtistSongs(artist);
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });
});
