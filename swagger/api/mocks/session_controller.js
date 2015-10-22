(function(){
  'use strict';

  module.exports = {
    session_info: session_info,
  }

  function session_info(req, res) {
    var sessionId = req.headers.sessionid;
    res.json({ userId: sessionId, userEmail: sessionId});
  }
})();
