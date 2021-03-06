(function(requirejs) {
  "use strict";

  // -- DEV RequireJS config --
  requirejs.config({
    // Packages = top-level folders; loads a contained file named "main.js"
    packages: ["common", "home", "user", "dashboard", "saveti", "live", "security", "stanari"],
    shim: {
      "jsRoutes" : {
        deps : [],
        // it's not a RequireJS module, so we have to tell it what var is returned
        exports : "jsRoutes"
      }
    },
    paths: {
      "jsRoutes" : "/jsroutes"
    }
  });

  requirejs.onError = function(err) {
    console.log(err);
  };

  // Load the app. This is kept minimal so it doesn't need much updating.
  require(["angular", "angular-cookies", "angular-route", "angular-resource", "jquery", "bootstrap", "ui-bootstrap", "ui-bootstrap-tpls", "./app"/*, "./ui-bootstrap-tpls-0.11.0"*/],
    function(angular) {
      angular.bootstrap(document, ["app"]);
    }
  );
})(requirejs);
