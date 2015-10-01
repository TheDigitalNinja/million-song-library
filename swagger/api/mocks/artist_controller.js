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

    var artistId = req.swagger.params.artistId.value;

    var artist = _.findWhere(data.artists, { artistId: artistId });

    res.json({
      artistId: artist.artistId,
      artist_mbid: artist.artist_mbid,
      artist_name: artist.artist_name,
      albums_list: artist.albums_list,
      link_to_image: artist.link_to_image,
      songs_list: artist.songs_list
    });
  }

})();
