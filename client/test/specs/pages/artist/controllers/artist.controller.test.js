/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import artistModule from 'pages/artist/artist.module.js';

describe('artistCtrl', () => {
  let $scope, $state, $stateParams, artistModel, createController;

  beforeEach(() => {
    angular.mock.module(artistModule, ($provide) => {
      $state = jasmine.createSpyObj('$state', ['go']);
      $stateParams = { artistId: '' };
      artistModel = jasmine.createSpyObj('artistModel', [
        'getArtist',
        'getArtistsById',
        'getArtistAlbums',
        'getArtistSongs',
      ]);

      $provide.value('$state', $state);
      $provide.value('$stateParams', $stateParams);
      $provide.value('artistModel', artistModel);
    });

    inject(($controller, $rootScope) => {
      $scope = $rootScope.$new();

      createController = () => {
        return $controller('artistCtrl', {
          artistModel: artistModel,
          $scope: $scope,
          $stateParams: $stateParams,
          $state: $state,
        });
      };
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should create an artist controller', () => {
    const artistCtrl = createController();
    expect(artistCtrl).toBeDefined();
  });

  it('should redirect to `home` state when $stateParams.artistId is empty string', () => {
    createController();
    expect($state.go).toHaveBeenCalledWith('msl.home');
  });

  it('should get artistInfo when stateParam artistId is defined', () => {
    $stateParams = { artistId: '1' };
    const ctrl = createController();
    expect(artistModel.getArtist).toHaveBeenCalledWith($stateParams.artistId, jasmine.any(Function));
  });

  describe('getArtist', () => {
    const similarArtistsList = ['1'];
    const albumsList = ['3'];
    const artist =  { similarArtistsList, albumsList };

    let ctrl;

    beforeEach(() => {
      $stateParams = { artistId: '1' };

      ctrl = createController();
      artistModel.getArtist.and.callFake((artistId, cb) => cb(artist));

      spyOn(ctrl, 'getSongs');
      spyOn(ctrl, 'getAlbums');
      spyOn(ctrl, 'getSimilarArtists');

      ctrl.getArtist();
    });

    it('should set the artist on the scope', () => {
      expect(ctrl.artist).toBe(artist);
    });

    it('should get the artist songs', () => {
      expect(ctrl.getSongs).toHaveBeenCalledWith(artist);
    });

    it('should get the albums', () => {
      expect(ctrl.getAlbums).toHaveBeenCalledWith(albumsList);
    });

    it('should get the similar artists', () => {
      expect(ctrl.getSimilarArtists).toHaveBeenCalledWith(similarArtistsList);
    });
  });

  describe('getSimilarArtists', () => {
    const similarArtistsList = ['1'];
    const artists = ['artist1'];
    let ctrl;

    beforeEach(() => {
      $stateParams = { artistId: '1' };

      ctrl = createController();
      artistModel.getArtistsById.and.callFake((list, cb) => cb(artists));

      ctrl.getSimilarArtists(similarArtistsList);
    });

    it('should ask the model for the artists', () => {
      expect(artistModel.getArtistsById).toHaveBeenCalledWith(similarArtistsList, jasmine.any(Function));
    });

    it('should set the scope similar artists fron the model response', () => {
      expect(ctrl.similarArtists).toEqual(artists);
    });
  });

  describe('getAlbums', () => {
    const albumsList = ['3'];
    const albums = ['album1'];
    let ctrl;

    beforeEach(() => {
      $stateParams = { artistId: '1' };

      ctrl = createController();
      artistModel.getArtistAlbums.and.callFake((list, cb) => cb(albums));

      ctrl.getAlbums(albumsList);
    });

    it('should ask the model for the albums', () => {
      expect(artistModel.getArtistAlbums).toHaveBeenCalledWith(albumsList, jasmine.any(Function));
    });

    it('should set the scope artist albums from the model response', () => {
      expect(ctrl.artistAlbums).toEqual(albums);
    });
  });

  describe('getSongs', () => {
    const artist = 'anArtist';
    const songs = ['song'];
    let ctrl;

    beforeEach(() => {
      $stateParams = { artistId: '1' };

      ctrl = createController();
      artistModel.getArtistSongs.and.callFake((artist, cb) => cb(songs));
      ctrl.getSongs(artist);
    });

    it('should ask the model for the songs', () => {
      expect(artistModel.getArtistSongs).toHaveBeenCalledWith(artist, jasmine.any(Function));
    });

    it('should set the scope artist albums from the model response', () => {
      expect(ctrl.songs).toEqual(songs);
    });
  });
});
