(function() {
  'use strict';

  module.exports = {
    login: login,
    logout: logout,
  };

  function login(req, res) {
    res.json({ data: { sessionToken: req.swagger.params.email.value } });
  }

  function logout(req, res) {
    res.json({ data: { message: 'successfully logged out' } });
  }

})();
