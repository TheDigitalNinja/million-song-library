(function() {
  'use strict';

  var data = require('./data.js');

  module.exports = {
    get_facet: get_facet,
  };

  function get_facet(req, res) {
    var facetId = req.swagger.params.facetId.value;

    var genreFacet = {
      facet_id: 'A1',
      name: 'genres',
      children: data.genres,
    };

    var ratingFacet = {
      facet_id: 'A2',
      name: 'ratings',
      children: data.ratings,
    };

    switch(facetId) {
      case '~':
        var allFacets = [ratingFacet, genreFacet];
        res.json({
          data: {
            facet_id: '00',
            name: 'root',
            children: allFacets,
          }
        });
        break;
      case 'A1':
        res.json({ data: genreFacet });
        break;
      case 'A2':
        res.json({ data: ratingFacet });
        break;
      default:
        var i;
        for(i = 0; i < data.genres.length; i++) {
          if (data.genres[i].facet_id === facetId) {
            res.json({ data: data.genres[i] });
          }
        }
        for(i = 0; i < data.ratings.length; i++) {
          if (data.ratings[i].facet_id === facetId) {
            res.json({ data: data.ratings[i] });
          }
        }
        break;
    }
  }

})();
