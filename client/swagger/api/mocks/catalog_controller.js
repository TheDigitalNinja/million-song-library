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
    res.json(album);
  }

  function browse_songs(req, res) {
    var facets = JSON.parse(req.swagger.params.facets.value || "{}");
    var genreName = facets.genre;
    var rating = facets.rating;
    var artist = facets.artist;

    res.json({ data: {
      last_pos: '',
      songs: filterCatalog(genreName, rating, artist),
    } } );
  }

  function browse_albums(req, res) {
    var genreName = req.swagger.params.facets.value;
    var albums;
    if(genreName) {
      albums = _.filter(data.albums, function(album) {
        return album.genre.toLowerCase() == genreName.toLowerCase();
      });
    }
    else {
       albums = data.albums;
    }
    res.json({
      last_pos: '',
      albums: albums,
    });
  }

  function browse_artists(req, res) {
    var genreName = req.swagger.params.facets.value;
    var artists;
    if(genreName) {
      artists = _.filter(data.artists, function(artist) {
        return artist.genre.toLowerCase() == genreName.toLowerCase();
      });
    }
    else {
       artists = data.artists;
    }
    res.json({ data: {
      last_pos: '',
      artists: artists,
    }});
  }

  function filterCatalog(genreName, rating, artist) {
    var res;
    if(genreName) {
       res = _.filter(data.songs, function(song) {
        return song.genre.toLowerCase() == genreName.toLowerCase();
      });
    }
    else {
      res = data.songs;
    }

    if(rating) {
       res = _.filter(res, function(song) { return song.average_rating >= rating; });
    }

    if(artist) {
      res = _.filter(res, function(song) { return song.artist_id == artist });
    }

    return res;
  }
})();
