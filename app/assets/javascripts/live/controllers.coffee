define(["angular"], (angular) ->
  LiveCtrl = ($scope, playRoutes,$timeout) ->
    timeoutSeconds = 5
    
    connect = () ->
      console.log("Connecting to ws ...")
      $scope.websocket = new WebSocket(playRoutes.controllers.Live.get().webSocketUrl())
      
      $scope.websocket.onopen = (event) ->
        $scope.$apply () ->
          $scope.message = "WS is open!"
      
      $scope.websocket.onmessage = (msg) ->
        $scope.$apply () ->
          $scope.message = msg.data
          console.log("received ws message: "+msg.data)
      
      $scope.websocket.onclose = (event) ->
        $scope.$apply () ->
          $scope.message = "WS closed!"
        console.log("received ws closed event")
        console.log("Scheduling re-connect in #{timeoutSeconds} seconds ...")
        $timeout(connect,timeoutSeconds*1000)
        
      $scope.websocket.onerror = (error) ->
        $scope.$apply () ->
          $scope.message = "WS error!"
        console.log("received ws error event: "+error)
        console.log("Scheduling re-connect in #{timeoutSeconds} seconds ...")
        $timeout(connect,timeoutSeconds*1000)
        
      $scope.send = (msg) ->
        if $scope.websocket.readyState == 1
          $scope.websocket.send(msg)
          console.log("sending websocket message: "+msg)
        else
          console.log("websocket not open, not sending!: "+msg)
        
    connect()
    
  LiveCtrl.$inject = ["$scope", "playRoutes", "$timeout"]
  
  Shit = () ->
    $scope.shit='OH'
  
  return {
    LiveCtrl : LiveCtrl
    Shit : Shit
  } 
)
