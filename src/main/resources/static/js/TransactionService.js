
'use strict';
 
angular.module('transactionStore').factory('TransactionService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {


            var factory = {
                loadCustomerTransactions: loadCustomerTransactions,
                saveTransaction: saveTransaction,
                convertTransaction: convertTransaction
            };

            return factory;

            function saveTransaction(data) {
               console.log('save transaction');
               var deferred = $q.defer();
               $http.post(urls.TRANSACTION_SERVICE_API + '/', JSON.stringify(data))
                   .then(
                       function (response) {
                           console.log('save transactions successfully');
//                           $localStorage.transactions = response.data;
                           deferred.resolve(response);
                       },
                       function (errResponse) {
                           console.error('Error while saving transaction');
                           deferred.reject(errResponse);
                       }
                   );
               return deferred.promise;
            };

            function loadCustomerTransactions() {
               console.log('Fetching all transactions');
               var deferred = $q.defer();
               $http.get(urls.TRANSACTION_SERVICE_API + '/')
                   .then(
                       function (response) {
                           console.log('Fetched transactions successfully');
                           $localStorage.transactions = response.data;
                           deferred.resolve(response);
                       },
                       function (errResponse) {
                           console.error('Error while loading transactions');
                           deferred.reject(errResponse);
                       }
                   );
              return deferred.promise;
            };

            function convertTransaction(id, country) {
               console.log('Fetching all transactions');
               var deferred = $q.defer();
               $http.get(urls.TRANSACTION_SERVICE_API + '/' + id + '/' + country)
                   .then(
                       function (response) {
                           console.log('Fetched converted transaction amount successfully');
                           deferred.resolve(response);
                       },
                       function (errResponse) {
                           console.error('Error while converting transaction');
                           deferred.reject(errResponse);
                       }
                   );
              return deferred.promise;
            };

        }
    ]);