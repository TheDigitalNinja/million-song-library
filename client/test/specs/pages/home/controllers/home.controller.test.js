/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import homePage from 'pages/home/home.module.js';

describe('homeCtrl', () => {

  const SONGS_LIST = ['song1', 'song2'];
  const ALBUMS_LIST = ['album1', 'album2'];
  const ARTISTS_LIST = ['artist1', 'artist2'];

  let $scope, $controller, homeCtrl;
  let songModel, albumModel, artistModel, $log;

  beforeEach(() => {
    angular.mock.module(homePage, ($provide) => {
      $log = jasmine.createSpyObj('$log', ['warn']);
      songModel = jasmine.createSpyObj('songModel', ['getSongs']);
      albumModel = jasmine.createSpyObj('albumModel', ['getAlbums']);
      artistModel = jasmine.createSpyObj('artistModel', ['getArtists']);

      $provide.value('$log', $log);
      $provide.value('songModel', songModel);
      $provide.value('albumModel', albumModel);
      $provide.value('artistModel', artistModel);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();

      homeCtrl = $controller('homeCtrl', {
        albumModel: albumModel,
        artistModel: artistModel,
        $log: $log,
        $scope: $scope,
        songModel: songModel,
      });
    });

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
  });

  describe('songsFiltered', () => {
    it('should set the songModel songs', () => {
      const songs = ['song'];
      homeCtrl.songsFiltered(songs);
      expect(songModel.songs).toBe(songs);
    });
  });

  describe('albumsFiltered', () => {
    it('should set the albumModel albums', () => {
      const albums = ['album'];
      homeCtrl.albumsFiltered(albums);
      expect(albumModel.albums).toBe(albums);
    });
  });

  describe('artistsFiltered', () => {
    it('should set the artistModel artists', () => {
      const artists = ['artist'];
      homeCtrl.artistsFiltered(artists);
      expect(artistModel.artists).toBe(artists);
    });
  });
});
