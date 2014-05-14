/**
 * Dashboard controllers.
 */
define(["angular"], function() {
  "use strict";

  /**
   * user is not a service, but stems from userResolve (Check ../user/services.js) object used by dashboard.routes.
   */
  var SavetiCtrl = function($scope, $location, $routeParams, $resource) {
  	var Savet = $resource('/savet/:id', {id:'@id'});
  	
    /*$scope.saveti = [
    	{ 
    	id : 1,
    	name : "Savet 1",
    	address : "Adresa saveta 1",
    	created_at : new Date()
    	},
    	{ 
    	id : 2,
    	name : "Savet 2",
    	address : "Adresa saveta 2",
    	created_at : new Date()
    	}
    ];*/
    
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
  			// Here we have ID if needed
  			//alert(savedSavet.id);
  			
  			// Do I need this to refresh the list?
  	  		$scope.saveti = Savet.query();
  	  		// NO, it's not working ..?
  	  		
  		});
  		
  		
  		
  		$location.path("/saveti");
  	}
  
  };
  SavetiCtrl.$inject = ["$scope", "$location", "$routeParams", "$resource"];
  
  

  return {
    SavetiCtrl: SavetiCtrl
    
  };

});
