(function() {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  module.exports = {
    genreList: genreList,
    getGenre: getGenre,
  };

  function genreList(req, res) {
    res.json({ genre: "" , genres: data.genres });
  }

  function getGenre(req, res) {
    var genreName = req.swagger.params.genreName.value;

    var genre = _findGenre(genreName);

    res.json({
      genre: genre.genreName,
      genres: genre.genres_list,
    });
  }

  function _findGenre(genreName) {
    var genre = _.filter(data.genres, function(genre) {
      return genre.genreName.toLowerCase() == genreName.toLowerCase();
    });

    return genre[0];
  }

})();
