(function() {
  'use strict';

  module.exports = {
    login: login,
  };

  function login(req, res) {
    res.json({ sessionToken: req.swagger.params.email.value });
  }
})();
