'use strict';

angular.module('mt-app')
    .controller('SystemCategoryController', ['$modalInstance', '$scope', 'ngTableParams','ApplicationService', 'DomainService','ACTION_INDEX',
    function ($modalInstance, $scope, ngTableParams, ApplicationService, DomainService, ACTION_INDEX) {
    console.info("SystemCategoryController");
    $scope.selectedCategories = [];
    $scope.storeList = [];
    $scope.allSelected = false;

    $scope.initSystemCategories = function() {
      if (! $scope.sysCatTableParams) {
        $scope.initSystemCategoriesTable();
      }
    };
    $scope.initSystemCategoriesTable = function() {
      $scope.sysCatTableParams = new ngTableParams({
          page: 1,            // show first page
          count: 10,          
      }, 
      {
          total: 0, // length of data
          getData: function($defer, params) {
            var pageDto = { };
              var page = params.page();
              var count = params.count();
              pageDto.pageNumber = page;
              pageDto.pageSize = count;
              
              var promise = DomainService.listSystemCategories(pageDto);
              promise.then(
                  function (responseData) {
                    if (responseData) {
                        $scope.systemCategories = responseData.contentList;
                        if (responseData.page != null) {
                            pageDto.totalRecords = responseData.page.totalRecords;
                            $scope.sysCatTableParams.total(pageDto.totalRecords);
                        } else {
                          $scope.systemCategories = [];
                          $scope.sysCatTableParams.total(0);
                        }
                    } else {
                      $scope.systemCategories = [];
                      $scope.sysCatTableParams.total(0);
                    }
                    $defer.resolve($scope.systemCategories);
                  },
                  function (reason) {
                      console.log('Failed: ' + reason);
                  }
              );
          }
      });
    };
    $scope.cancel = function(){
        $modalInstance.dismiss('Canceled');
    }; // end cancel
    
    $scope.import = function(){
        $modalInstance.close(data);
    };
    $scope.selectAll = function() {
        $scope.selectedCategories = [];
        $scope.allSelected = !$scope.allSelected;
        console.info("selectAll " + $scope.allSelected);
        if ($scope.allSelected) {
          angular.forEach($scope.systemCategories, function(c) {
            c.$$selected = $scope.allSelected;
            $scope.selectedCategories.push(c);
          });
        } else {
          $scope.selectedCategories = [];
        }
    }; 
    $scope.select = function(c) {  
        c.$$selected = !c.$$selected;
        if (c.$$selected) {
          c.defaultAccountId = undefined; 
          $scope.selectedCategories.push(c);
        } else {
          c.$$selected = false;
          $scope.allSelected = false;
          var index = $scope.selectedCategories.indexOf(c);
          if (index > -1) {
              $scope.selectedCategories.splice(index, 1);
          }
        }
        if ($scope.selectedCategories.length == $scope.transactions.length) {
            $scope.allSelected = true;
        }
    };
}]);
