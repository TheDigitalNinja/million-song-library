/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import homePage from 'pages/home/home.module.js';

describe('homeCtrl', () => {

  const SONGS_LIST = ['song1', 'song2'];
  const ALBUMS_LIST = ['album1', 'album2'];
  const ARTISTS_LIST = ['artist1', 'artist2'];

  let $scope, $controller, homeCtrl;
  let songModel, albumModel, artistModel, $log, filterModel, $location;

  beforeEach(() => {
    angular.mock.module(homePage, ($provide) => {
      $log = jasmine.createSpyObj('$log', ['warn']);
      songModel = jasmine.createSpyObj('songModel', ['getSongs']);
      albumModel = jasmine.createSpyObj('albumModel', ['getAlbums']);
      artistModel = jasmine.createSpyObj('artistModel', ['getArtists']);
      filterModel = jasmine.createSpyObj('filterModel', ['applyCurrentFilters']);
      $location = jasmine.createSpyObj('$location', ['search']);

      $provide.value('$log', $log);
      $provide.value('songModel', songModel);
      $provide.value('albumModel', albumModel);
      $provide.value('artistModel', artistModel);
      $provide.value('filterModel', filterModel);
      $provide.value('$location', $location);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();

      homeCtrl = () => {
        return $controller('homeCtrl', {
          albumModel: albumModel,
          artistModel: artistModel,
          $log: $log,
          $scope: $scope,
          songModel: songModel,
        });
      };
    });

    $location.search.and.returnValue({});

  });

  afterEach(() => {
    $scope.$destroy();
  });

  describe('constructor', () => {
    it('should get the list of songs', (done) => {
      (async () => {
        songModel.getSongs.and.returnValue({ songs: SONGS_LIST });
        await songModel.getSongs($scope);

        expect(songModel.getSongs).toHaveBeenCalled();
        done();
      })();
    });

    it('should get the list of albums', (done) => {
      (async () => {
        albumModel.getAlbums.and.returnValue({ albums: ALBUMS_LIST });
        await albumModel.getAlbums($scope);

        expect(albumModel.getAlbums).toHaveBeenCalled();
        done();
      })();
    });

    it('should get the list of artists', (done) => {
      (async () => {
        artistModel.getArtists.and.returnValue({ artists: ARTISTS_LIST });
        await artistModel.getArtists($scope);

        expect(artistModel.getArtists).toHaveBeenCalled();
        done();
      })();
    });

    it('should get the current selected tab', () => {
      $location.search.and.returnValue({ tab: 'albums' });
      expect(homeCtrl().selectedTab).toBe(1);
    });

    it('should use 0 as default selected tab', () => {
      $location.search.and.returnValue({ tab: 'nonExistingTab' });
      expect(homeCtrl().selectedTab).toBe(0);
    });

    it('should apply current filters again when deleted from library', () => {
      const controller = homeCtrl();
      $scope.$emit('deletedFromLibrary');
      expect(filterModel.applyCurrentFilters.calls.count()).toEqual(2);
    });

    it('should apply current filters again when added to library', () => {
      const controller = homeCtrl();
      $scope.$emit('addedToLibrary');
      expect(filterModel.applyCurrentFilters.calls.count()).toEqual(2);
    });
  });

  describe('selectTab', () => {
    it('should set the tab attribu on the search query', () => {
      const tab = 'songs';
      const controller = homeCtrl();
      controller.selectTab(tab);
      expect($location.search).toHaveBeenCalledWith('tab', tab);
    });
  });

  describe('songsFiltered', () => {
    it('should set the songModel songs', () => {
      const songs = ['song'];
      homeCtrl().songsFiltered(songs);
      expect(songModel.songs).toBe(songs);
    });
  });

  describe('albumsFiltered', () => {
    it('should set the albumModel albums', () => {
      const albums = ['album'];
      homeCtrl().albumsFiltered(albums);
      expect(albumModel.albums).toBe(albums);
    });
  });

  describe('artistsFiltered', () => {
    it('should set the artistModel artists', () => {
      const artists = ['artist'];
      homeCtrl().artistsFiltered(artists);
      expect(artistModel.artists).toBe(artists);
    });
  });
});
