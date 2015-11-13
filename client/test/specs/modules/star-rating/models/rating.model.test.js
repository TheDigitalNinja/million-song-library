import ratingModule from 'modules/star-rating/module.js';

describe('ratingModel', () => {

  const SONG_ID = '1';
  const ALBUM_ID = '1';
  const ARTIST_ID = '1';
  const RATING = 3;

  let ratingModel, rateSongStore, rateAlbumStore, rateArtistStore;

  beforeEach(() => {
    angular.mock.module(ratingModule, ($provide) => {
      rateSongStore = jasmine.createSpyObj('rateSongStore', ['push']);
      rateAlbumStore = jasmine.createSpyObj('rateAlbumStore', ['push']);
      rateArtistStore = jasmine.createSpyObj('rateArtistStore', ['push']);

      $provide.value('rateSongStore', rateSongStore);
      $provide.value('rateAlbumStore', rateAlbumStore);
      $provide.value('rateArtistStore', rateArtistStore);
    });

    inject((_ratingModel_) => {
      ratingModel = _ratingModel_;
    });
  });

  it('should instantiate the model', () => {
    expect(ratingModel).toBeDefined();
  });

  describe('rate', () => {
    it('should push the song rating', (done) => {
      (async () => {
        await ratingModel.rate(SONG_ID, 'song', RATING);
        expect(rateSongStore.push).toHaveBeenCalledWith(SONG_ID, RATING);
        done();
      })();
    });

    it('should push the album rating', (done) => {
      (async () => {
        await ratingModel.rate(ALBUM_ID, 'album', RATING);
        expect(rateAlbumStore.push).toHaveBeenCalledWith(ALBUM_ID, RATING);
        done();
      })();
    });

    it('should push the artist rating', (done) => {
      (async () => {
        await ratingModel.rate(ARTIST_ID, 'artist', RATING);
        expect(rateArtistStore.push).toHaveBeenCalledWith(ARTIST_ID, RATING);
        done();
      })();
    });
  });
});
