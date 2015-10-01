(function () {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  module.exports = {
    getAlbums: getAlbums,
    getAlbumById: getAlbumById,
  };

  function getAlbums(req, res) {
    res.json({ albums: data.albums });
  }

  function getAlbumById(req, res) {

    var albumId = req.swagger.params.albumId.value;

    var album = _.findWhere(data.albums, { album_id: albumId });

    res.json({
      albumId: album.album_id,
      album_name: album.album_name,
      albums_year: album.album_year,
      link_to_image: album.link_to_image,
      songs_list: album.songs_list,
      artist: album.artist,
      genre: album.genre
    });
  }

})();
