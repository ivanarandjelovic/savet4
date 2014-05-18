define(["angular"], (angular) ->
  LiveCtrl = ($scope, playRoutes) ->
    $scope.websocket = new WebSocket(playRoutes.controllers.Live.get().webSocketUrl())
    
    $scope.websocket.onmessage = (msg) ->
      $scope.message = msg.data
      console.log("received ws message: "+msg.data)
      
    $scope.send = (msg) ->
      $scope.websocket.send(msg)
      console.log("sending websocket message: "+msg)
    
  LiveCtrl.$inject = ["$scope", "playRoutes"]
  
  Shit = () ->
    $scope.shit='OH'
  
  return {
    LiveCtrl : LiveCtrl
    Shit : Shit
  } 
)
