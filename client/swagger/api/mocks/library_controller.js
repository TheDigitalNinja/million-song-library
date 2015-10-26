(function() {
  'use strict';

  var data = require('./data.js');
  var _ = require('lodash');

  var libraries = {};

  module.exports = {
    song_library: song_library,
    add_to_library: add_to_library,
  }

  function song_library(req, res) {
    var sessionId = req.headers.sessionid;
    var sondIds = libraries[sessionId] || [];

    var songs = _.filter(data.songs, function(song){
      return _.includes(sondIds, song.song_id);
    });

    res.json({ songs: songs || [] });
  }

  function add_to_library(req, res) {
    var sessionId = req.headers.sessionid;
    var songIds = libraries[sessionId] || [];
    songIds.push(req.swagger.params.songId.value);

    libraries[sessionId] = songIds;

    res.json({ message: "Song added to your library" });
  }
})();
