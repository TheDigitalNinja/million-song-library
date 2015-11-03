(function () {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  module.exports = {
    getAlbums: getAlbums,
    getAlbumById: getAlbumById,
  };

  function getAlbums(req, res) {
    var genre = req.swagger.params.genreName.value;
    if (genre !== undefined && genre !== null && genre.length > 0) {
      res.json({data: getAlbumsFilteredByGenre(genre)});
    } else {
      res.json({ data: { albums: data.albums } });
    }
  }

  function getAlbumById(req, res) {
    var albumId = req.swagger.params.albumId.value;
    var album = _.findWhere(data.albums, { album_id: albumId });
    res.json({ data: album });
  }

  function getAlbumsFilteredByGenre(genre) {
    var response = [];
    for (var i = data.albums.length - 1; i >= 0; i--) {
      if (data.albums[i].genre_name.toLowerCase() === genre.toLowerCase()) {
        response.push(data.albums[i]);
      }
    }
    return {albums: response};
  }

})();
