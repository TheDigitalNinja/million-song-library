(function() {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  module.exports = {
    get_song: get_song,
    get_artist: get_artist,
    get_album: get_album,
    browse_songs: browse_songs,
    browse_albums: browse_albums,
    browse_artists: browse_artists,
  };

  function get_song(req, res) {
    var songId = req.swagger.params.songId.value;
    var song = _.findWhere(data.songs, { song_id: songId }) || {};
    res.json({ data: song });
  }

  function get_artist(req, res) {
    var artistId = req.swagger.params.artistId.value;
    var artist = _.findWhere(data.artists, { artist_id: artistId });
    res.json({ data: artist });
  }

  function get_album(req, res) {
    var albumId = req.swagger.params.albumId.value;
    var album = _.findWhere(data.albums, { album_id: albumId });
    res.json({ data: album });
  }

  function browse_songs(req, res) {
    var facets = req.swagger.params.facets.value || "";
    var facetsList = facets.split(',');
    var genreId, ratingId;

    for(var i = 0; i < facetsList.length; i++) {
      var facetId = facetsList[i];

      if(isRatingFilter(facetId)) {
        ratingId = facetId;
      }
      else if(isGenreFilter(facetId)) {
        genreId = facetId;
      }
    }

    res.json({ data: {
      last_pos: '',
      songs: filterCatalog(data.songs, genreId, ratingId),
    } } );
  }

  function browse_albums(req, res) {
    var facets = req.swagger.params.facets.value || "";
    var facetsList = facets.split(',');
    var genreId, ratingId;

    for(var i = 0; i < facetsList.length; i++) {
      var facetId = facetsList[i];

      if(isRatingFilter(facetId)) {
        ratingId = facetId;
      }
      else if(isGenreFilter(facetId)) {
        genreId = facetId;
      }
    }

    res.json({ data: {
      last_pos: '',
      albums: filterCatalog(data.albums, genreId, ratingId),
    }});
  }

  function browse_artists(req, res) {
    var facets = req.swagger.params.facets.value || "";
    var facetsList = facets.split(',');
    var genreId, ratingId;

    for(var i = 0; i < facetsList.length; i++) {
      var facetId = facetsList[i];

      if(isRatingFilter(facetId)) {
        ratingId = facetId;
      }
      else if(isGenreFilter(facetId)) {
        genreId = facetId;
      }
    }

    res.json({ data: {
      last_pos: '',
      artists: filterCatalog(data.artists, genreId, ratingId),
    }});
  }

  function filterCatalog(catalog, genreId, ratingId) {
    var res;
    if(genreId) {
      var genre = _.find(data.genres, { facet_id: genreId });
       res = _.filter(catalog, function(entity) {
        return entity.genre.toLowerCase() == genre.name.toLowerCase();
      });
    }
    else {
      res = catalog;
    }

    if(ratingId) {
      var ratingFilter = _.find(data.ratings, { facet_id: ratingId });
      var rating = parseInt(ratingFilter.name[0]);
      res = _.filter(res, function(entity) { return entity.average_rating >= rating; });
    }

    return res;
  }

  function isGenreFilter(facetId) {
   var genres = data.genres;
   var facetIds = _.pluck(genres, 'facet_id');
   return _.includes(facetIds, facetId);
  }

  function isRatingFilter(facetId) {
   var ratings = data.ratings;
   var facetIds = _.pluck(ratings, 'facet_id');
   return _.includes(facetIds, facetId);
  }
})();
