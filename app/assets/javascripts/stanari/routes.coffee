###
  Saveti routes.
###
define(["angular", "./controllers", "common"], (angular, controllers) ->
  "use strict"

  mod = angular.module("stanari.routes", ["savet4.common"])
  mod.config(["$routeProvider", "userResolve", ($routeProvider, userResolve) ->
    $routeProvider
	    .when("/stanari/:savetId",  {templateUrl: "/assets/templates/stanari/stanari.html",  controller:controllers.StanariCtrl, resolve:userResolve})
	    #.when("/saveti",  {templateUrl: "/assets/templates/saveti/saveti.html",  controller:controllers.SavetiCtrl, resolve:userResolve})
	    #.when("/saveti/create",  {templateUrl: "/assets/templates/saveti/create.html",  controller:controllers.SavetiCtrl, resolve:userResolve})
  ])
  mod
)
