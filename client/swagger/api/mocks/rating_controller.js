(function() {
  'use strict';

  module.exports = {
    rate_album: rateAlbum,
    rate_song: rateSong,
    rate_artist: rateArtist,
  };

  function rateAlbum(req, res) {
    var albumId = req.swagger.params.albumId.value;
    var rating = req.swagger.params.rating.value;

    res.json({ data: { message: "successfully updated album " + albumId + " with rating: " + rating } });
  }

  function rateSong(req, res) {
    var songId = req.swagger.params.songId.value;
    var rating = req.swagger.params.rating.value;

    res.json({ data: { message: "successfully updated song " + songId + " with rating: " + rating } });
  }

  function rateArtist(req, res) {
    var artistId = req.swagger.params.artistId.value;
    var rating = req.swagger.params.rating.value;

    res.json({ data: { message: "successfully updated artist " + artistId + " with rating: " + rating } });
  }

})();
