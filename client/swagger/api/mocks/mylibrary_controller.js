(function() {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  module.exports = {
    get_my_library: get_my_library,
    add_song: add_song,
    add_album: add_album,
  }

  function get_my_library(req, res) {
    var albums = _.filter(data.albums, { 'in_my_library': true });
    var artists = _.filter(data.artists, { 'in_my_library': true });
    var songs = _.filter(data.songs, { 'in_my_library': true });

    var response = { data: { albums: albums, artists: artists, songs: songs } };
    res.json(response);
  }

  function add_song(req, res) {
    res.json({ data: { message: "Song " + req.swagger.params.songId.value + " added to the library" } });
  }

  function add_album(req, res) {
    res.json({ data: { message: "Album " + req.swagger.params.albumId.value + " added to the library" } });
  }
})();
