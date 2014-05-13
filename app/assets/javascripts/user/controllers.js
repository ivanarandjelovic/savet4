/**
 * User controllers.
 */
define(["angular"], function(angular) {
  "use strict";

  var LoginCtrl = function($scope, $location, userService) {
    $scope.credentials = {};
    $scope.loginFailure = false;
    
    $scope.login = function(credentials) {
      $scope.loginFailure = false;

      var loginUser = userService.loginUser(credentials).then(function(user) {
	      if(user === undefined) {
	    	  $scope.loginFailure = true;
	      } else {
	        $location.path("/dashboard");
	      }
      });
    };
  };
  LoginCtrl.$inject = ["$scope", "$location", "userService"];

  return {
    LoginCtrl: LoginCtrl
  };

});
