###
 * Saveti controllers.
###
define(["angular"], () ->

  "use strict"

  SavetiCtrl = ($scope, $location, $routeParams, $resource) ->

    Savet = $resource('/savet/:id', {id:'@id'})

    savetId = $routeParams.id

    if savetId 
    	$scope.savet = Savet.get( {id :savetId} )
    else
    	$scope.saveti = Savet.query()

    $scope.open = (id) ->
      $location.path("/stanari/"+id)

    $scope.showCreate = () ->
      $location.path("/saveti/create")

    $scope.create =  (savet) ->
      savetNew = new Savet(savet)
      savetNew.$save(null, (savedSavet) ->
        $scope.saveti = Savet.query()
        $location.path("/saveti")
      )

  SavetiCtrl.$inject = ["$scope", "$location", "$routeParams", "$resource"]
  
  {
    SavetiCtrl: SavetiCtrl
  }
)
