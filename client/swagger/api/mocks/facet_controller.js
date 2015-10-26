(function() {
  'use strict';

  var data = require('./data.js');

  module.exports = {
    get_facet: get_facet,
  };

  function get_facet(req, res) {
    res.json({
      facet_id: '0',
      name: 'genres',
      children: data.genres,
    });
  }

})();
