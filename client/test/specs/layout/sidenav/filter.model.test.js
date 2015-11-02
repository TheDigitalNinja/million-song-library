/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import sidenav from 'layout/sidenav/sidenav.module.js';

describe('ratingFilterDirective', () => {
  const RATING = '4';
  const GENRE = '2';
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
      spyOn(filterModel, '_filterSongs');
      spyOn(filterModel, '_filterAlbums');
      spyOn(filterModel, '_filterArtists');
      spyOn(filterModel, '_getFacets');

      filterModel.filter(listener);
    });

    it('should assing the listener', () => {
      expect(filterModel.listener).toEqual(listener);
    });

    it('should filter songs', () => {
      expect(filterModel._filterSongs).toHaveBeenCalled();
    });

    it('should filter albums', () => {
      expect(filterModel._filterAlbums).toHaveBeenCalled();
    });

    it('should filter artists', () => {
      expect(filterModel._filterArtists).toHaveBeenCalled();
    });
  });

  describe('filterSongs', () => {
    beforeEach(() => {
      filterModel.selectedRating = RATING;
      filterModel.selectedGenre = GENRE;
      filterModel.listener = listener;
    });

    it('should call model filter method', () => {
      filterModel._filterSongs();
      expect(songModel.filterSongs).toHaveBeenCalledWith(filterModel._getFacets(), jasmine.any(Function));
    });

    it('should call the listener', () => {
      const songs = ['song1', 'song2'];

      songModel.filterSongs.and.callFake((facets, done) => {
        done(songs);
      });

      filterModel._filterSongs();
      expect(listener.songsFiltered).toHaveBeenCalledWith(songs);
    });
  });

  describe('filterAlbums', () => {
    beforeEach(() => {
      filterModel.selectedRating = RATING;
      filterModel.selectedGenre = GENRE;
      filterModel.listener = listener;
    });

    it('should call model filter method', () => {
      filterModel._filterAlbums();
      expect(albumModel.filterAlbums).toHaveBeenCalledWith(filterModel._getFacets(), jasmine.any(Function));
    });

    it('should call the listener', () => {
      const albums = ['album1', 'album2'];

      albumModel.filterAlbums.and.callFake((facets, done) => {
        done(albums);
      });

      filterModel._filterAlbums();
      expect(listener.albumsFiltered).toHaveBeenCalledWith(albums);
    });
  });

  describe('filterArtists', () => {
    beforeEach(() => {
      filterModel.selectedRating = RATING;
      filterModel.selectedGenre = GENRE;
      filterModel.listener = listener;
    });

    it('should call model filter method', () => {
      filterModel._filterArtists();
      expect(artistModel.filterArtists).toHaveBeenCalledWith(filterModel._getFacets(), jasmine.any(Function));
    });

    it('should call the listener', () => {
      const artists = ['artist1', 'artist2'];

      artistModel.filterArtists.and.callFake((facets, done) => {
        done(artists);
      });

      filterModel._filterArtists();
      expect(listener.artistsFiltered).toHaveBeenCalledWith(artists);
    });
  });

  describe('getFacets', () => {
    it('should concatenate the rating and genre Ids on the returned string', () => {
      filterModel.selectedRating = RATING;
      filterModel.selectedGenre = GENRE;
      filterModel.listener = listener;
      expect(filterModel._getFacets()).toEqual('4,2');
    });

    it('should return just the rating id when the genre is null', () => {
      filterModel.selectedRating = RATING;
      filterModel.selectedGenre = null;
      filterModel.listener = listener;
      expect(filterModel._getFacets()).toEqual('4');
    });

    it('should return just the genre id when the rating is null', () => {
      filterModel.selectedRating = null;
      filterModel.selectedGenre = GENRE;
      filterModel.listener = listener;
      expect(filterModel._getFacets()).toEqual('2');
    });

    it('should return an empty string when the rating and genre are null', () => {
      filterModel.selectedRating = null;
      filterModel.selectedGenre = null;
      filterModel.listener = listener;
      expect(filterModel._getFacets()).toEqual(undefined);
    });
  });
});
