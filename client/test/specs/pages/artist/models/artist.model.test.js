/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import artistModule from 'pages/artist/artist.module.js';

describe('artistModel', () => {

  const ARTIST_ID = 5;

  let artistModel, artistStore, catalogStore, $log;

  beforeEach(() => {
    angular.mock.module(artistModule);
    inject(($injector) => {
      artistModel = $injector.get('artistModel');
      artistStore = $injector.get('artistStore');
      catalogStore = $injector.get('catalogStore');
      $log = $injector.get('$log');
    });
  });

  it('should instantiate the model', () => {
    expect(artistModel).toBeDefined();
  });

  describe('getArtist', () => {
    it('should fetch the artist information', (done) => {
      (async () => {
        spyOn(artistStore, 'fetch');
        spyOn(catalogStore, 'fetch');
        await artistModel.getArtist(ARTIST_ID);
        expect(artistStore.fetch).toHaveBeenCalledWith(ARTIST_ID);
        done();
      })();
    });
  });

  describe('getArtists', () => {
    it('should get the list of artists', (done) => {
      (async () => {
        spyOn(artistStore, 'fetchAll');
        await artistModel.getArtists();
        done();
      })();
      expect(artistStore.fetchAll).toHaveBeenCalled();
    });
  });

  describe('similarArtists', () => {
    it('should fetch the artist', (done) => {
      (async () => {
        const similarArtistIds = ['1', '2'];
        spyOn(artistStore, 'fetch');
        artistStore.fetch.and.returnValue({ similarArtistsList: similarArtistIds });
        await artistModel.getSimilarArtists(ARTIST_ID);
        expect(artistStore.fetch).toHaveBeenCalledWith(ARTIST_ID);
        done();
      })();
    });

    it('should fetch the similar artists', (done) => {
      (async () => {
        const similarArtistIds = ['3', '4'];
        spyOn(artistStore, 'fetch');
        artistStore.fetch.and.returnValue({ similarArtistsList: similarArtistIds });
        await artistModel.getSimilarArtists(ARTIST_ID);
        expect(artistStore.fetch).toHaveBeenCalledWith('3');
        expect(artistStore.fetch).toHaveBeenCalledWith('4');
        done();
      })();
    });
  });
});
