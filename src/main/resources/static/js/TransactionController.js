'use strict';
 
angular.module('transactionStore').controller('TransactionController',
    ['TransactionService', '$scope',  function( TransactionService, $scope) {
 
        var self = this;
        self.transaction = {};
        self.transactions=[];

        self.submit = submit;
        self.reset = reset;
 
        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.maxLength = 1000;
        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;
 
        function submit() {
            console.log('Loading transaction list');

            TransactionService.loadCustomerTransactions()
                .then(
                    function (response) {
                        console.log('Transactions loaded successfully');
                        self.successMessage = 'Transactions loaded successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.transactions=response.data;
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while loading Transactions');
                        self.errorMessage = 'Error while loading Transactions: ' + errResponse.data.message;
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