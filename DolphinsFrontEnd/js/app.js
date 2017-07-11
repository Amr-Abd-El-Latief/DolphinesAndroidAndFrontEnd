var app = angular.module('app', []);

app.controller('mainController', ['$scope', '$http', function($scope, $http) {
    $scope.searchParams = {};
    $scope.isSearchResults = false;
    
    $scope.searchDB = function() {
        //Using Mocked Data
        /*$http.get('./json/mockedData.json')
        .then(function (result) {
            if(result.length > 0) {
                $scope.searchResults = result;
                $scope.isSearchResults = true;
            }
        });*/
        
        //Using Backend DB
        $http.get('https://dolphinsbackend.mybluemix.net/images')
        .then(function(result) {
            $scope.searchResults = result;
            $scope.isSearchResults = true;  
        }, function(error) {
            console.log("Error: " + error);
        });
    };
}]);