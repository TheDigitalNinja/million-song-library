(function () {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  module.exports = {
    artistList: artistList,
    getArtist: getArtist,
    getArtistAlbums: getArtistAlbums,
    getSimilarArtists: getSimilarArtists,
  };

  function artistList(req, res) {
    var genre = req.swagger.params.genreName.value;
    if (genre !== undefined && genre !== null && genre.length > 0) {
      res.json(getArtistFilteredByGenre(genre));
    } else {
      res.json({ artists: data.artists });
    }
  }

  function getArtist(req, res) {
    var artistId = req.swagger.params.artistId.value;
    var artist = _.findWhere(data.artists, { artistId: artistId });
    res.json(artist);
  }

  function getArtistAlbums(req, res) {
    var artistId = req.swagger.params.artistId.value;
    var artist = _.findWhere(data.artists, {artistId: artistId});
    var albums = [];
    for (var i = 0; i < artist.albums_list.length; i++) {
      for (var j = 0; j < data.albums.length; j++) {
        if (artist.albums_list[i] === data.albums[j].album_id) {
          var album = data.albums[j];
          albums.push(album);
        }
      }
    }
    res.json({ albums: albums });
  }

  function getSimilarArtists(req, res) {
    var artists = _.sample(data.artists, 3);

    res.json({ artists: artists });
  }

  function getArtistFilteredByGenre(genre) {
    var response = [];
    for (var i = data.artists.length - 1; i >= 0; i--) {
      if (data.artists[i].genre.toLowerCase() === genre.toLowerCase()) {
        response.push(data.artists[i]);
      }
    }
    return { artists: response };
  }

})();
