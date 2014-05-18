define(["angular"], (angular) ->
  LiveCtrl = ($scope, playRoutes) ->
    $scope.websocket = new WebSocket(playRoutes.controllers.Live.get().webSocketUrl())
    
    $scope.websocket.onmessage = (msg) ->
      $scope.message = msg.data
      console.log("received ws message: "+msg.data)
      
    $scope.websocket.onclose = (event) ->
      $scope.message = "WS closed!"
      console.log("received ws closed event")
      
    $scope.send = (msg) ->
      if $scope.websocket.readyState == 1
        $scope.websocket.send(msg)
        console.log("sending websocket message: "+msg)
      else
        console.log("websocket not open, not sending!: "+msg)
        
    
  LiveCtrl.$inject = ["$scope", "playRoutes"]
  
  Shit = () ->
    $scope.shit='OH'
  
  return {
    LiveCtrl : LiveCtrl
    Shit : Shit
  } 
)
