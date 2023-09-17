'use strict';
 
angular.module('transactionStore').controller('TransactionController',
    ['TransactionService', '$scope', 'CountryList',  function( TransactionService, $scope, CountryList) {

        var self = this;
        self.transaction = {};
        self.transactions=[];
        self.countries = CountryList.countries;

        self.submit = submit;
        self.reset = reset;
        self.reload = reload;
        self.convertTransactionAmount = convertTransactionAmount;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.maxLength = 1000;
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
        self.onlyDate = /^([0-9]{4}[-][0-9]{2}[-][0-9]{2})|([0-9]{8})/;

        function submit() {

            // save transaction
            var data = {
            description: self.transaction.description,
            amount: self.transaction.amount,
            transactionDate: self.transaction.date
            };

            TransactionService.saveTransaction(data)
                .then(
                    function (response) {
                        console.log('Transaction saved successfully');
                        self.successMessage = 'Transaction saved successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.transaction={};
                        $scope.myForm.$setPristine();

                        // reload the list of transactions
                        reload();
                    },
                    function (errResponse) {
                        console.error('Error while saving Transaction');
                        self.errorMessage = 'Error while saving Transaction: ' + JSON.stringify(errResponse.data);
                        self.successMessage='';
                    }
                );
        }

        function convertTransactionAmount(id, country) {

            return TransactionService.convertTransaction(id, country)
                .then(
                    function (response) {
                        console.log('Transaction converted successfully');
                        self.errorMessage='';
                        self.done = true;
                        var foundIndex = self.transactions.findIndex(x => x.id == response.data.id);
                        self.transactions[foundIndex] = response.data;
                    },
                    function (errResponse) {
                        console.error('Error while converting Transaction');
                        self.errorMessage = 'Error while converting Transaction: ' + JSON.stringify(errResponse.data);
                        self.successMessage='';
                    }
                );

        }

        function reload() {
            TransactionService.loadCustomerTransactions()
                .then(
                    function (response) {
                        console.log('Transactions loaded successfully');
                        self.errorMessage='';
                        self.done = true;
                        self.transactions=response.data;
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while loading Transactions');
                        self.errorMessage = 'Error while loading Transactions: ' + JSON.stringify(errResponse.data);
                        self.successMessage='';
                    }
                );
        }

        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.transaction={};
            self.transactions=[];
            $scope.myForm.$setPristine(); //reset Form
        }
    }
    ]);