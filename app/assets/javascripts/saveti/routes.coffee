###
  Saveti routes.
###
define(["angular", "./controllers", "common"], (angular, controllers) ->
  "use strict"

  mod = angular.module("saveti.routes", ["savet4.common"])
  mod.config(["$routeProvider", "userResolve", ($routeProvider, userResolve) ->
    $routeProvider
	    .when("/saveti/open/:id",  {templateUrl: "/assets/templates/saveti/open.html",  controller:controllers.SavetiCtrl, resolve:userResolve})
	    .when("/saveti",  {templateUrl: "/assets/templates/saveti/saveti.html",  controller:controllers.SavetiCtrl, resolve:userResolve})
	    .when("/saveti/create",  {templateUrl: "/assets/templates/saveti/create.html",  controller:controllers.SavetiCtrl, resolve:userResolve})
  ])
  mod
)
