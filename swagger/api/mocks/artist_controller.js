(function() {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  module.exports = {
    artistList: artistList,
    getArtist: getArtist,
  };

  function artistList(req, res) {
    res.json({ artists: data.artists });
  }

  function getArtist(req, res) {
    var artist_mbid = req.swagger.params.artist_mbid.value;

    var artist = _findArtist(artist_mbid);

    res.json({
      artistId: artist.artistId,
      artist_mbid: artist.artist_mbid,
      artist_name: artist.artist_name,
      albums_list: artist.albums_list,
      link_to_image: artist.link_to_image,
      songs_list: artist.songs_list
    });
  }

  function _findArtist(artist_mbid) {
    var artist = _.findWhere(data.artists, { artist_mbid: artist_mbid });

    return artist[0];
  }

})();
