(function() {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  module.exports = {
    getSongById: getSongById,
    browseCatalog: browseCatalog,
  };

  function getSongById(req, res) {
    var songId = req.swagger.params.songId.value;
    var song = _.findWhere(data.songs, { song_id: songId }) || {};
    res.json(song);
  }

  function browseCatalog(req, res) {
    var genreName = req.swagger.params.genre.value;
    var rating = req.swagger.params.rating.value;
    var artist = req.swagger.params.artist.value;

    res.json({
      genre: '',
      songs: filterCatalog(genreName, rating, artist),
    });
  }

  function filterCatalog(genreName, rating, artist) {
    var res;
    if(genreName) {
       res = _.filter(data.songs, function(song) {
        return song.genre_name.toLowerCase() == genreName.toLowerCase();
      });
    }
    else {
      res = data.songs;
    }

    if(rating) {
       res = _.filter(res, function(song) { return song.average_rating >= rating; });
    }

    if(artist) {
      res = _.filter(res, function(song) { return song.artist_mbid == artist });
    }

    return res;
  }
})();
