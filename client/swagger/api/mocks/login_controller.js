(function() {
  'use strict';

  module.exports = {
    login: login,
    logout: logout,
  };

  function login(req, res) {
    res.setHeader('Set-Cookie', 'sessionToken=' + req.swagger.params.email.value + '; path=/; HttpOnly');

    res.json({ data: { authenticated: true } });
  }

  function logout(req, res) {
    res.json({ data: { message: 'successfully logged out' } });
  }

})();
