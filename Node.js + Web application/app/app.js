(function(){
	angular.module('ev3', [])
		.controller('movementController', function($scope, $http){

			
			$scope.movement = function(letter){

				var move = "";
				var time = $scope.time;
				if (time == null || time < 0){
					time = 3.0;
				}

				switch(letter){
					case "A" :
						move = "forward";
						window.sendinSocket(move, time);
						break;
					case "B" :
						move = "backward";
						window.sendinSocket(move, time);
						break;
					case "C" :
						move = "left";
						window.sendinSocket(move, time);
						break;
					case "D" :
						move = "right";
						window.sendinSocket(move, time);
						break;
				}
				var data2send = {
					movement : move
				};

				$http.post('/api/movement',data2send).success(function(response){
						console.log("Success");
				}).error(function(err){
					console.log(err);
				})

			}

		});
}());