
var exec = cordova.require('cordova/exec');

module.exports = {
  makeAsyncUpdateSecurityProvider: function(onfulfilled, onrejected) {
    exec(onfulfilled, onrejected, 'CDVSecurityProvider', 'makeAsyncUpdateSecurityProvider', []);
  }
}
