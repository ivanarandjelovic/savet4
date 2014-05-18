define(["angular", "./routes", "./controllers"], (angular, routes, controllers) ->
  "use strict"

  mod = angular.module("savet4.live", ["ngCookies", "ngRoute", "live.routes"])
  
  mod.controller("LiveCtrl", controllers.LiveCtrl)
  
  mod
)
