/**
 * Common functionality.
 */
define(["angular", "./services/helper", "./services/playRoutes", "./filters", "./directives/example"],
    function(angular) {
  "use strict";

  return angular.module("savet4.common", ["common.helper", "common.playRoutes", "common.filters",
    "common.directives.example"]);
});
