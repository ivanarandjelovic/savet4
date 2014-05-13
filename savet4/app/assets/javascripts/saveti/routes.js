/**
 * Dashboard routes.
 */
define(["angular", "./controllers", "common"], function(angular, controllers) {
  "use strict";

  var mod = angular.module("saveti.routes", ["savet4.common"]);
  mod.config(["$routeProvider"/*, "userResolve"*/, function($routeProvider/*, userResolve*/) {
    $routeProvider
      .when("/saveti/open/:id",  {templateUrl: "/assets/templates/saveti/open.html",  controller:controllers.SavetiCtrl/*, resolve:userResolve*/})
      .when("/saveti",  {templateUrl: "/assets/templates/saveti/saveti.html",  controller:controllers.SavetiCtrl/*, resolve:userResolve*/})
      .when("/saveti/create",  {templateUrl: "/assets/templates/saveti/create.html",  controller:controllers.SavetiCtrl/*, resolve:userResolve*/});
      //.when("/admin/dashboard",  {templateUrl: "/assets/templates/dashboard/admin.html",  controller:controllers.AdminDashboardCtrl})
  }]);
  return mod;
});
