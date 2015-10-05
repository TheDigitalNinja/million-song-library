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

    res.json(album);
  }

})();
