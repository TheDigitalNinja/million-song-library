(function() {
  'use strict';

  module.exports = {
    rateAlbum: rateAlbum,
    rateSong: rateSong,
    rateArtist: rateArtist,
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
    var artistId = req.swagger.params.songId.value;
    var rating = req.swagger.params.rating.value;

    res.json({ data: { message: "successfully updated artist " + artistId + " with rating: " + rating } });
  }

})();
