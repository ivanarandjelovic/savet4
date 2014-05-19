/**
 * Saveti controllers.
 */
define(["angular"], function() {
  "use strict";

  var SavetiCtrl = function($scope, $location, $routeParams, $resource) {
  	var Savet = $resource('/savet/:id', {id:'@id'});
  	
    var savetId = $routeParams.id;
    
    if(savetId) {
    	$scope.savet = Savet.get( {id :savetId} );
    } else {
    	$scope.saveti = Savet.query();
    }
    
    $scope.open = function(id) {
  		$location.path("/saveti/open/"+id);
  	}
  	
  	$scope.showCreate = function() {
  		$location.path("/saveti/create");
  	}
  	
  	$scope.create = function (savet) {
  		var savetNew = new Savet(savet);
  		savetNew.$save(null, function (savedSavet) {
  	  		$scope.saveti = Savet.query();
  	  		$location.path("/saveti");
  		});
  	}
  
  };
  SavetiCtrl.$inject = ["$scope", "$location", "$routeParams", "$resource"];

  return {
    SavetiCtrl: SavetiCtrl
  };

});
