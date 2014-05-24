/**
 * User package module.
 * Manages all sub-modules so other RequireJS modules only have to import the package.
 */
define(["angular", "./retry_queue", "./interceptor"], function(angular) {
  "use strict";

  return angular.module("savet4.security", ["security.interceptor", "security.retryQueue"]);
});
