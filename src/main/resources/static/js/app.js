angular.module('app', ['ngResource', 'ngRoute'])
    // if login/pass is incorrect server sends response with Www-Authenticate Basic realm="Realm" header
    // cause that some browsers popup additional window with login form
    .config(function ($routeProvider, $httpProvider) {
        // to avoid that behaviour add X-Requested-With header and XMLHttpRequest as its value
        // now response will be send without Www-Authenticate header cause server can differ for example asynchronous AJAX request and standard POST request
        $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
        $routeProvider
            .when('/list', {
                templateUrl: 'partials/list.html',
                controller: 'ListController',
                controllerAs: 'listCtrl'
            })
            .when('/details/:id', {
                templateUrl: 'partials/details.html',
                controller: 'DetailsController',
                controllerAs: 'detailsCtrl'
            })
            .when('/new', {
                templateUrl: 'partials/new.html',
                controller: 'NewController',
                controllerAs: 'newCtrl'
            })
            // add login routing
            .when('/login', {
                templateUrl: 'partials/login.html',
                controller: 'AuthenticationController',
                controllerAs: 'authCtrl'
            })
            .otherwise({
                redirectTo: '/list'
            });
    })
    // add login endpoint as constant
    .constant('LOGIN_ENDPOINT', '/login')
    .constant('PLAYER_ENDPOINT', '/api/players/:id')
    .factory('Player', function ($resource, PLAYER_ENDPOINT) {
        return $resource(PLAYER_ENDPOINT);
    })
    .service('Players', function (Player) {
        this.getAll = function () {
            return Player.query();
        }
        this.get = function (index) {
            return Player.get({id: index})
        }
        this.add = function (player) {
            player.$save();
        }
    })
    // add Authentication service with $htttp in order to send request and LOGIN_ENDPOINT constant
    .service('AuthenticationService', function($http, LOGIN_ENDPOINT) {
        this.authenticate = function(credentials, successCallback) {
            // Basic authentication is to send request with header: Authorization: Basic stringxyz where stringxyz is char sequence encoded with base64 method
            // this string contains username and password stored in form of username:password
            // btoa() is browser implemented method that allows to encode data
            var authHeader = {Authorization: 'Basic ' + btoa(credentials.username+':'+credentials.password)};
            // assign newly created object as a header
            var config = {headers: authHeader};
            // send POST request to login endpoint with defined config(authorization header)
            $http
                .post(LOGIN_ENDPOINT, {}, config)
                // define success callback if user is authenticated properly
                .then(function success(value) {
                    successCallback();
                    // and error call back (401 status) if not
                }, function error(reason) {
                    console.log('Login error');
                    console.log(reason);
                });
        }
    })
    .controller('ListController', function (Players) {
        var vm = this;
        vm.players = Players.getAll();
    })
    .controller('DetailsController', function ($routeParams, Players) {
        var vm = this;
        var playerIndex = $routeParams.id; // player index equals id parameter (line 11)
        vm.player = Players.get(playerIndex);
    })
    .controller('NewController', function (Players, Player) {
        var vm = this;
        vm.player = new Player();
        vm.savePlayer = function () {
            Players.add(vm.player);
            vm.player = new Player();
        }
    })
    // add AuthenticationController
    .controller('AuthenticationController', function($rootScope, $location, AuthenticationService) {
        var vm = this;
        // credentials object is binded with form (username and password fields)
        vm.credentials = {};
        // after successful login application sets global var authenticated to true and redirects user to /new page
        var loginSuccess = function() {
            // $rootScope is superior application context and allows to refer to authenticated variable in different html files
            $rootScope.authenticated = true;
            // $location allows to dynamically change URL address
            $location.path('/new');
        }
        // login function runs when "login" button in form is clicked
        // it check if username and password are valid based on authenticate method defined in AuthenticationService
        vm.login = function() {
            AuthenticationService.authenticate(vm.credentials, loginSuccess);
        }
    });