/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import homePage from 'pages/home/home.module.js';

describe('homeCtrl', () => {

  const SONGS_LIST = ['song1', 'song2'];
  const ALBUMS_LIST = ['album1', 'album2'];
  const ARTISTS_LIST = ['artist1', 'artist2'];

  let $scope, $controller, homeCtrl;
  let genreFilterModel, catalogStore, albumStore, artistStore;
  let createController;

  beforeEach(() => {
    angular.mock.module(homePage, ($provide) => {
      const $log = jasmine.createSpyObj('$log', ['warn']);
      genreFilterModel = jasmine.createSpyObj('genreFilterModel', ['selectedGenre']);
      catalogStore = jasmine.createSpyObj('catalogStore', ['fetch']);
      albumStore = jasmine.createSpyObj('albumStore', ['fetchAll']);
      artistStore = jasmine.createSpyObj('artistStore', ['fetchAll']);

      $provide.value('$log', $log);
      $provide.value('genreFilterModel', genreFilterModel);
      $provide.value('catalogStore', catalogStore);
      $provide.value('albumStore', albumStore);
      $provide.value('artistStore', artistStore);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();

      homeCtrl = $controller('homeCtrl', {
        $scope: $scope,
      });
    });

  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should get the list of songs', (done) => {
    (async () => {
      catalogStore.fetch.and.returnValue({ songs: SONGS_LIST });
      await homeCtrl.getSongs();

      expect(catalogStore.fetch).toHaveBeenCalled();
      expect(homeCtrl.songs).toEqual(SONGS_LIST);
      done();
    })();
  });

  it('should get the list of albums', (done) => {
    (async () => {
      albumStore.fetchAll.and.returnValue({ albums: ALBUMS_LIST });
      await homeCtrl.getAlbums();

      expect(albumStore.fetchAll).toHaveBeenCalled();
      expect(homeCtrl.albumsList).toEqual(ALBUMS_LIST);
      done();
    })();
  });

  it('should get the list of artists', (done) => {
    (async () => {
      artistStore.fetchAll.and.returnValue({ artists: ARTISTS_LIST });
      await homeCtrl.getArtists();

      expect(artistStore.fetchAll).toHaveBeenCalled();
      expect(homeCtrl.artists).toEqual(ARTISTS_LIST);
      done();
    })();
  });

});
