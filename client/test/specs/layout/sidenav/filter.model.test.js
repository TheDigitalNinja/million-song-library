/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import sidenav from 'layout/sidenav/sidenav.module.js';

describe('ratingFilterDirective', () => {
  const RATING = 4;
  const GENRE = 'rock';
  let filterModel, listener, songModel, albumModel, artistModel;

  beforeEach(() => {
    angular.mock.module(sidenav, ($provide) => {
      songModel = jasmine.createSpyObj('songModel', ['filterSongs']);
      albumModel = jasmine.createSpyObj('albumModel', ['filterAlbums']);
      artistModel = jasmine.createSpyObj('artistModel', ['filterArtists']);

      $provide.value('songModel', songModel);
      $provide.value('albumModel', albumModel);
      $provide.value('artistModel', artistModel);
    });

    inject((_filterModel_) => {
      filterModel = _filterModel_;
    });

    listener = jasmine.createSpyObj('listener', ['artistsFiltered', 'songsFiltered', 'albumsFiltered']);
  });

  describe('filter', () => {
    beforeEach(() => {
      spyOn(filterModel, 'filterSongs');
      spyOn(filterModel, 'filterAlbums');
      spyOn(filterModel, 'filterArtists');

      filterModel.filter({ rating: RATING, genre: GENRE }, listener);
    });

    it('should apply the filters', () => {
      expect(filterModel.minRating).toEqual(RATING);
      expect(filterModel.selectedGenre).toEqual(GENRE);
    });

    it('should assing the listener', () => {
      expect(filterModel.listener).toEqual(listener);
    });

    it('should filter songs', () => {
      expect(filterModel.filterSongs).toHaveBeenCalled();
    });

    it('should filter albums', () => {
      expect(filterModel.filterAlbums).toHaveBeenCalled();
    });

    it('should filter artists', () => {
      expect(filterModel.filterArtists).toHaveBeenCalled();
    });
  });

  describe('filterSongs', () => {
    beforeEach(() => {
      filterModel.minRating = RATING;
      filterModel.selectedGenre = GENRE;
      filterModel.listener = listener;
    });

    it('should call model filter method', () => {
      filterModel.filterSongs();
      expect(songModel.filterSongs).toHaveBeenCalledWith(RATING, GENRE, jasmine.any(Function));
    });

    it('should call the listener', () => {
      const songs = ['song1', 'song2'];

      songModel.filterSongs.and.callFake((rating, genre, callback) => {
        callback(songs);
      });

      filterModel.filterSongs();
      expect(listener.songsFiltered).toHaveBeenCalledWith(songs);
    });
  });

  describe('filterAlbums', () => {
    beforeEach(() => {
      filterModel.minRating = RATING;
      filterModel.selectedGenre = GENRE;
      filterModel.listener = listener;
    });

    it('should call model filter method', () => {
      filterModel.filterAlbums();
      expect(albumModel.filterAlbums).toHaveBeenCalledWith(RATING, GENRE, jasmine.any(Function));
    });

    it('should call the listener', () => {
      const albums = ['album1', 'album2'];

      albumModel.filterAlbums.and.callFake((rating, genre, callback) => {
        callback(albums);
      });

      filterModel.filterAlbums();
      expect(listener.albumsFiltered).toHaveBeenCalledWith(albums);
    });
  });

  describe('filterArtists', () => {
    beforeEach(() => {
      filterModel.minRating = RATING;
      filterModel.selectedGenre = GENRE;
      filterModel.listener = listener;
    });

    it('should call model filter method', () => {
      filterModel.filterArtists();
      expect(artistModel.filterArtists).toHaveBeenCalledWith(RATING, GENRE, jasmine.any(Function));
    });

    it('should call the listener', () => {
      const artists = ['artist1', 'artist2'];

      artistModel.filterArtists.and.callFake((rating, genre, callback) => {
        callback(artists);
      });

      filterModel.filterArtists();
      expect(listener.artistsFiltered).toHaveBeenCalledWith(artists);
    });
  });
});
