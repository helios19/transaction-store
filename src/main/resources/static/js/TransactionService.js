
'use strict';
 
angular.module('transactionStore').factory('TransactionService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {
 
            var factory = {
                loadCustomerTransactions: loadCustomerTransactions
            };
 
            return factory;

             function loadCustomerTransactions() {
                console.log('Fetching all transaction summaries');
                var deferred = $q.defer();
                $http.get(urls.TRANSACTION_SERVICE_API + '/all')
                    .then(
                        function (response) {
                            console.log('Fetched successfully client product transactions');
                            $localStorage.transactions = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading transactions');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
             }

        }
    ]);