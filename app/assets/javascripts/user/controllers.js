/**
 * User controllers.
 */
define([ "angular" ], function(angular) {
	"use strict";

	var LoginCtrl = function($scope, $location, userService) {
		$scope.credentials = {
			email : "a@a.a",
			password : "1"
		};
		$scope.loginFailure = false;

		$scope.login = function(credentials) {
			$scope.loginFailure = false;

			var loginUser = userService.loginUser(credentials).then(
					function(user) {
						if (user === undefined) {
							$scope.loginFailure = true;
						} else {
							$location.path("/dashboard");
						}
					});
		};
	};
	LoginCtrl.$inject = [ "$scope", "$location", "userService" ];

	// Please note that $modalInstance represents a modal window (instance)
	// dependency.
	// It is not the same as the $modal service used above.

	var ModalLoginCtrl = function($scope, $modalInstance, userService) {

		$scope.credentials = {
			email : "a@a.a",
			password : "1"
		};
		$scope.loginFailure = false;

		$scope.login = function(credentials) {
			$scope.loginFailure = false;

			var loginUser = userService.loginUser(credentials).then(
					function(user) {
						if (user === undefined) {
							$scope.loginFailure = true;
						} else {
							$modalInstance.close(user);
						}
					});
		};

		$scope.cancel = function() {
			$modalInstance.dismiss('cancel');
		};

	};
	ModalLoginCtrl.$inject = [ "$scope", "$modalInstance", "userService" ];

	return {
		LoginCtrl : LoginCtrl,
		ModalLoginCtrl : ModalLoginCtrl
	};

});
