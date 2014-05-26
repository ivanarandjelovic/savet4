###
 * Stanari controllers.
###
define(["angular"], () ->

  "use strict"

  StanariCtrl = ($scope, $location, $routeParams, $resource) ->

    Savet = $resource('/savet/:id', {id:'@id'})
    Stanari = $resource("/stanari/:savetId", {savetId : "@savetId"})

    savetId = $routeParams.savetId
    $scope.savet = Savet.get({id:savetId})

    $scope.stanari = Stanari.query( {savetId : $routeParams.savetId})


  StanariCtrl.$inject = ["$scope", "$location", "$routeParams", "$resource"]
  
  {
    StanariCtrl: StanariCtrl
  }
)
