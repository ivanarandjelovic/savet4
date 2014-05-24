define(["angular"], (angular) ->
  LiveCtrl = ($scope, playRoutes,$timeout, userService) ->
    timeoutSeconds = 5
    
    connect = () ->
      if not $scope.user
        console.log("WS: no user, closing socket if any and returning")
        $scope.websocket?.close?()
        return
      
      console.log("Connecting to ws ...")
      $scope.websocket = new WebSocket(playRoutes.controllers.Live.getSecured().webSocketUrl())
      
      $scope.websocket.onopen = (event) ->
        $scope.$apply () ->
          $scope.message = "WS is open!"
      
      $scope.websocket.onmessage = (msg) ->
        $scope.$apply () ->
          $scope.message = msg.data
          console.log("received ws message: "+msg.data)
          if msg.data == "Not auth!" 
            $scope.websocket.close()
            console.log("I have closed ws!")
                  
      $scope.websocket.onclose = (event) ->
        $scope.$apply () ->
          $scope.message = "WS closed!"
        console.log("received ws closed event")
        if not $scope.user
          console.log("No re-connect scheduled, no user!")
        else
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
    
    #Watch for user change (login or logout)
    $scope.$watch( ( -> $scope.user ) , ( -> connect() ) )
    
  LiveCtrl.$inject = ["$scope", "playRoutes", "$timeout", "userService"]
  
  return {
    LiveCtrl : LiveCtrl
  } 
)
