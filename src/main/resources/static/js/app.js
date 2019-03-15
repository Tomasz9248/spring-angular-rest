// define module named app to manage index.html and PlayerController with #http object injection
angular.module('app', []).controller('PlayerController', function($http) {
    var vm = this;
    function refreshData() {
        // send GET request to endpoint defined in spring
        $http.get('api/players')
            // if response status is ok assign data from endpoint to players variable
        .then(function success(response) {
            vm.players = response.data;
        }, function error(response) {
            // if not log error in console with response status
            console.log('API error ' + response.status);
        });
    }

    vm.addPlayer = function(player) {
        // send POST request to endpoint defined in spring
        $http.post('api/players', player)
            .then(function success(response) {
                // if response status is ok refresh data and add player into db
            refreshData();
            vm.player = {};
        }, function error(response) {
                // if not log error in console with object data
            console.log('Data not saved ' + player);
        });
    };
    vm.appName = 'Player Manager';
    // use refreshData func every time page is refreshed
    refreshData();
});