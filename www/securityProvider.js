
exports.makeAsyncUpdateSecurityProvider = function(onfulfilled, onrejected) {
  cordova.exec(onfulfilled, onrejected, 'CDVSecurityProvider', 'makeAsyncUpdateSecurityProvider', []);
};
