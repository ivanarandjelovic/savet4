define(["angular", "./controllers"], (angular, controllers) ->
  "use strict"

  mod = angular.module("savet4.live", ["ngCookies", "ngRoute"])
  
  mod.controller("LiveCtrl", controllers.LiveCtrl)
  
  mod
)
