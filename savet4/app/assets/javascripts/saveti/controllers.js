/**
 * Dashboard controllers.
 */
define(["angular"], function() {
  "use strict";

  /**
   * user is not a service, but stems from userResolve (Check ../user/services.js) object used by dashboard.routes.
   */
  var SavetiCtrl = function($scope) {
   
  };
  SavetiCtrl.$inject = ["$scope"];

  return {
    SavetiCtrl: SavetiCtrl
    
  };

});
