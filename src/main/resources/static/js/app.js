// add ngRescource module to connect to db and ngRoute to enable routing
angular.module('app', ['ngResource', 'ngRoute'])
    // config which html file should be loaded on index page in <div ng-view></div> section under specific url
    .config(function ($routeProvider) {
        $routeProvider
            .when('/list', { // url path
                templateUrl: 'partials/list.html', // html file to load
                controller: 'ListController', // name of the controller
                controllerAs: 'listCtrl' // controller alias (no need to define in list.html file)
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
            .otherwise({ // otherwise is like else in if statement
                redirectTo: '/list' // redirect to this address when requested url does not match any of defined above
            });
    })
    // define endpoint url as constant to avoid typos and keep code more readable
    .constant('PLAYER_ENDPOINT', '/api/players/:id')
    // inject $resource into factory it returns constructor with methods to access data from endpoint
    .factory('Player', function ($resource, PLAYER_ENDPOINT) {
        return $resource(PLAYER_ENDPOINT);
    })
    // service allows to retrieve data from endpoint with methods provided by $resource
    .service('Players', function (Player) {
        this.getAll = function () {
            return Player.query();
        }
        this.get = function (index) {
            return Player.get({id:index})
        }
        this.add = function (player) {
            player.$save();
        }
    })
    // ListController (injected with Players service) gets all records from endpoint and assigns them to players attribute in model
    .controller('ListController', function (Players) {
        var vm = this;
        vm.players = Players.getAll();
    })
    // Inject Players service and $routeParams service that allows to get parameter from url address
    .controller('DetailsController', function ($routeParams, Players) {
        var vm = this;
        var playerIndex = $routeParams.id; // player index equals id parameter (line 11)
        vm.player = Players.get(playerIndex);
    })

    // NewController is injected with Players service and Player service
    // form fields are binded with model named player. After button click in form savePlayer() method is called and player is saved in db
    // to set up fields as empty method creates new object
    .controller('NewController', function (Players, Player) {
        var vm = this;
        vm.player = new Player();
        vm.savePlayer = function () {
            Players.add(vm.player);
            vm.player = new Player();
        }
    });