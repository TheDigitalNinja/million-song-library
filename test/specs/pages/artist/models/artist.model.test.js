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
        spyOn(artistStore, 'fetchArtistAlbums');
        spyOn(catalogStore, 'fetch');
        await artistModel.getArtist(ARTIST_ID);
        done();
      })();
      expect(artistStore.fetch).toHaveBeenCalledWith(ARTIST_ID);
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

  describe('getSimilarArtists', () => {
    it('should fetch the similar artist', (done) => {
      (async () => {
        spyOn(artistStore, 'fetchSimilarArtist');
        await artistModel.getSimilarArtists(ARTIST_ID);
        done();
      })();
      expect(artistStore.fetchSimilarArtist).toHaveBeenCalledWith(ARTIST_ID);
    });
  });

});
