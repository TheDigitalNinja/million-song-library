(function() {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  module.exports = {
    get_my_library: get_my_library,
    add_song: add_song,
    add_album: add_album,
    add_artist: add_artist,
    remove_song: remove_song,
    remove_album: remove_album,
    remove_artist: remove_artist,
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

  function add_artist(req, res) {
    res.json({ data: { message: "Artist " + req.swagger.params.artistId.value + " added to the library" } });
  }

  function remove_song(req, res) {
    res.json({ data: { message: "Song " + req.swagger.params.songId.value + " removed to the library" } });
  }

  function remove_album(req, res) {
    res.json({ data: { message: "Album " + req.swagger.params.albumId.value + " removed to the library" } });
  }

  function remove_artist(req, res) {
    res.json({ data: { message: "Artist " + req.swagger.params.artistId.value + " removed to the library" } });
  }
})();
