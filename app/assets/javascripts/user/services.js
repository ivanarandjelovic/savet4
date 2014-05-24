/**
 * User service, exposes user model to the rest of the app.
 */
define([ "angular", "./controllers", "ui-bootstrap", "common" ], function(angular, controllers) {
	"use strict";

	var mod = angular.module("user.services",
			[ "savet4.common", "ui.bootstrap" ]);
	mod.factory("userService", [
			"$http",
			"$q",
			"playRoutes",
			function($http, $q, playRoutes) {
				var user, token;
				return {
					loginUser : function(credentials) {
						return playRoutes.controllers.Application.login().post(
								credentials).then(
								function(response) {
									// return promise so we can chain easily
									token = response.data.token;
									// in a real app we could use the token to
									// fetch the
									// user data
									return playRoutes.controllers.Users
											.userForToken(token).get().then(
													function(response) {
														user = response.data; // Extract
														// user
														// data
														// from
														// user() request
														// user.email =
														// credentials.email;
														return user;
													});
								}, function(response) {
									// Login failure case!
									return undefined;
								});
					},
					logout : function() {
						// Logout on server in a real app
						return playRoutes.controllers.Application.logout()
								.post().then(function(response) {
									user = undefined;
								});

					},
					getUser : function() {
						return user;
					},
					refreshUser : function() {
						var u = playRoutes.controllers.Users.currentUser()
								.get().then(function(response) {
									user = response.data; // Extract user
									return user;
									// data from
								});
					}
				};
			} ]);
	/**
	 * Add this object to a route definition to only allow resolving the route
	 * if the user is logged in. This also adds the contents of the objects as a
	 * dependency of the controller.
	 */
	mod.constant("userResolve", {
		user : [ "$q", "userService", function($q, userService) {
			var deferred = $q.defer();
			var user = userService.getUser();
			if (user) {
				deferred.resolve(user);
			} else {
				deferred.reject();
			}
			return deferred.promise;
		} ]
	});

	mod.factory("securityService", [ "$http", "$q", "playRoutes", "$modal", "$log",
			function($http, $q, playRoutes, $modal, $log) {
				return {
					showLogin : function() {

							var modalLogin = $modal.open({
								templateUrl : '/assets/templates/user/modalLogin.html',
								controller : controllers.ModalLoginCtrl,
								size : "lg"
							});

							modalLogin.result.then(function(user) {
								$log.info('Login done for : ' + user);
							}, function() {
								$log.info('Modal dismissed at: ' + new Date());
							});
						}
				};
			} ]);

	/**
	 * If the current route does not resolve, go back to the start page.
	 */
	var handleRouteError = function($rootScope, $location) {
		$rootScope.$on("$routeChangeError", function(e, next, current) {
			$location.path("/");
		});
	};
	handleRouteError.$inject = [ "$rootScope", "$location" ];
	mod.run(handleRouteError);
	return mod;
});
