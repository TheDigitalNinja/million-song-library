(function() {
  'use strict';

  module.exports = {
    registration: registration,
  };

  function registration(req, res) {
    res.json({ data: { message: 'successfully registration' } });
  }

})();
