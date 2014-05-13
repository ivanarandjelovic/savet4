/**
 * Home controllers.
 */
define(["angular"], function(angular) {
			"use strict";

			/** Controls the index page */
			var HomeCtrl = function($scope, $rootScope, $location, helper) {
				console.log(helper.sayHi());
				$rootScope.pageTitle = "Welcome";
			};
			HomeCtrl.$inject = ["$scope", "$rootScope", "$location", "helper"];

			/** Controls the header */
			var HeaderCtrl = function($scope, userService, helper, $location) {
				// Wrap the current user from the service in a watch expression
				$scope.$watch(function() {
							var user = userService.getUser();
							return user;
						}, function(user) {
							$scope.user = user;
						}, true);

				$scope.logout = function() {
					userService.logout();
					$scope.user = undefined;
					$location.path("/");
				};

				$scope.home = function() {
					if ($scope.user === undefined) {
						$location.path("/");
					} else {
						$location.path("/dashboard");
					}
				};

				$scope.saveti = function() {
					$location.path("/saveti");
				};

			};
			HeaderCtrl.$inject = ["$scope", "userService", "helper",
					"$location"];

			/** Controls the footer */
			var FooterCtrl = function(/* $scope */) {
			};
			// FooterCtrl.$inject = ["$scope"];

			return {
				HeaderCtrl : HeaderCtrl,
				FooterCtrl : FooterCtrl,
				HomeCtrl : HomeCtrl
			};

		});
