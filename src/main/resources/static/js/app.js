
var app = angular.module('transactionStore',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8081',
    TRANSACTION_SERVICE_API : 'http://localhost:8081/transactions',
    RATE_EXCHANGE_SERVICE_API : 'http://localhost:8081/rate-exchanges'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'templates/list.html',
                controller:'TransactionController',
                controllerAs:'ctrl',
                resolve: {
                    CountryList: ['$q','$http', function($q,$http) {

                        var deferred = $q.defer();
                        $http.get('http://localhost:8081/rate-exchanges/countries')
                           .then(
                               function (response) {
                                   console.log('Fetched rate exchange countries successfully');
                                   deferred.resolve({countries:response.data});
                               },
                               function (errResponse) {
                                   console.error('Error while fetching rate exchange countries');
                                   deferred.reject(errResponse);
                               }
                           );
                        return deferred.promise;
                    }]
                }
            });

        $urlRouterProvider
        .otherwise('/');
    }]);