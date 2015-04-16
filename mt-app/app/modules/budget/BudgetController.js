'use strict';

angular.module('mt-app')
    .controller('BudgetController', ['$rootScope', '$scope', '$filter', '$state', '$window',
    	'ApplicationService', 'DomainService', 'TransactionService', 'BudgetService',
    	function ($rootScope, $scope, $filter, $state, $window, ApplicationService, DomainService, TransactionService, BudgetService) {
    console.info("BudgetController")
    $scope.treeData = [];
    $scope.transactionSearchDto = { };
    $scope.today = new Date();
    $rootScope.$on('budgetSearchChanged', function(event, data) {
      console.info("budgetSearchChanged");
      console.dir(data);
      $scope.budgetSearchChanged(data);
    });
    $scope.init = function () {
      console.info("BudgetController:init");
      $scope.searchDto = BudgetService.searchDto;
        BudgetService.init();
    	//DomainService.init();
      $scope.loadBudgets();
      $scope.loadCategoryTree();
    };
    $scope.budgetSearchChanged = function(searchDto) { // searchDto
      $scope.searchDto.fromDate = searchDto.fromDate;
      $scope.searchDto.toDate = searchDto.toDate;
      console.info("$scope.searchDto.budgetId " + $scope.searchDto.budgetId);
      $scope.showBudgetedVsSpentReport();
    };
    $scope.loadBudgets =function() {
        var promise = BudgetService.listBudget($scope.searchDto);
        promise.then(
            function (data) {
                $scope.budgets = data;
                if (data.length > 0) {
                  if (data[0].id) {
                    $scope.selectedBudget = data[0].id;
                    $scope.searchDto.budgetId = $scope.selectedBudget;
                    $scope.loadBudgetItems($scope.selectedBudget);
                    $scope.budgetSelectionChanged();
                  }
                }
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );
    };
    $scope.loadCategoryTree =function() {
        var promise = DomainService.listCategoryTree(DomainService.searchDto);
        promise.then(
            function (data) {
                $scope.treeData = data;
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );
    };
    $scope.newBudget = function(budgetName) {
    	console.info("$scope.budgetName");
    	console.info(budgetName);
    	var newBudget = angular.copy(BudgetService.newBudget);
    	newBudget.name = budgetName;
    	BudgetService.save(newBudget);
    };
    $scope.catTreeColDefs = [
        { field: "Name", displayName: "Name"},
        { field: "transactionTypeCode", displayName: "Type"}
    ];
    $scope.onCategorySelect = function(branch) {
        console.dir(branch);
    };
    $scope.budgetSelectionChanged = function() {
        $scope.showBudgetedVsSpentReport();
        $scope.loadBudgetItems($scope.searchDto.budgetId);
    };
    $scope.loadBudgetItems =function(id) {
        console.info("loadBudgetItems" + id);
        if (id == undefined) {
            return;
        }
        var promise = BudgetService.listBudgetItems($scope.searchDto);
        promise.then(
            function (data) {
                $scope.budgetItems = data;
                // BudgetService.searchDto.page.pageSize = 10;
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );
    };
    $scope.sum = function(item) {
        var sum = 0;
        if (item.children) {
            for(var i = 0; i < item.children.length; i++) {
                var child = item.children[i];
                sum += child.amount;
            }
        } else {
            sum = item.amount;
        }
        return sum;
    };
    $scope.edit = function(item) {
        $scope.original = angular.copy(item);
        item.$$edit = true;
    };
    $scope.cancel = function(item) {
        item.$$edit = false;
        item = $scope.original;
    };
    $scope.categoryChanged = function(budgetItem) {
        console.info("cat changed");
        console.info(budgetItem.categoryId);
        if(budgetItem.categoryId && $scope.categories.length) {
          var selected = $filter('filter')($scope.categories, {id: budgetItem.categoryId});
          budgetItem.category = selected[0];
        }
    };
    $scope.periodChanged = function(budgetItem) {
        console.info("period changed");
        console.info(budgetItem.periodCode);
        if(budgetItem.periodCode && $rootScope.periods.length) {
          var selected = $filter('filter')($rootScope.periods, {code: budgetItem.periodCode});
          budgetItem.period = selected[0];
        }
        console.dir(budgetItem);
    };
    $scope.saveBudgetItem = function(item) {
        console.info("saveBudgetItem");
        console.dir(item);
        if (item.id == undefined || item.id == null) {
            item.action.actionIndex = $rootScope.ACTION_NEW;
        } else {
            item.action.actionIndex = $rootScope.ACTION_UPDATE;
        }
        var items = [];
        items.push(item);
        var promise = BudgetService.saveBudgetItems(items);
        promise.then(
            function (data) {
                item = data;
                item.$$edit = false;
                $scope.budgetItems.shift();
                console.dir($scope.budgetItems);
                $scope.budgetItems.unshift(item);
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );
    };
    $scope.add = function() {
        $scope.inserted = angular.copy(BudgetService.newBudgetItem);
        $scope.createNewBudgetItem($scope.inserted);
    };
    $scope.addChild = function(budgetItem) {
        $scope.inserted = angular.copy(BudgetService.newBudgetItem);
        console.info("addChild");
        console.dir($scope.selectedBudget);
        $scope.inserted.budgetId = $scope.selectedBudget;
        $scope.inserted.$$edit = true;
        if (! budgetItem.children) {
          budgetItem.children = [];
        }
        budgetItem.children.unshift($scope.inserted);
    };
    $scope.createNewBudgetItem = function(budgetItem) {
        if ($scope.budgetItems == undefined) {
            $scope.budgetItems = [];
        }
        budgetItem.budgetId = $scope.selectedBudget;
        $scope.budgetItems.unshift(budgetItem);
        budgetItem.$$edit = true;
        console.dir(budgetItem);
    };
    $scope.showBudgetedVsSpentReport = function() {
        var dto = $scope.searchDto;
        dto.page.pageSize = 25;
        var promise = BudgetService.budgetedAndSpentReport(dto);
        promise.then(
            function (data) {
                $scope.budgetedVsSpentReport = data;
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );

        var totBudgetedPromise = BudgetService.getTotalBudgeted(dto);
        totBudgetedPromise.then(
            function (data) {
                $scope.totalBudgeted = data;
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );

        var transactionSearchDto = $scope.transactionSearchDto;
        transactionSearchDto.fromDate = dto.fromDate;
        transactionSearchDto.toDate = dto.toDate;
        var totSpentPromise = TransactionService.getExpenseTotal(transactionSearchDto);
        totSpentPromise.then(
            function (data) {
                $scope.totalSpent = data;
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );
    };
    $scope.openFromDate = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.fromDateOpened = true;
      };
    $scope.openToDate = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.toDateOpened = true;
      };
      $scope.getTooltip = function(budgetItem) {
        if (budgetItem.$$hideRows) {
            return "Expand";
        } else {
            return "Collapse";
        }
    };
    $scope.thisMonth = function() {
      $scope.setRange($scope.today);
    };
    $scope.prevMonth = function() {
      var prevMonth = ApplicationService.prevMonth($scope.searchDto.fromDate);
      $scope.setRange(prevMonth);
      $scope.showBudgetedVsSpentReport();
    };
    $scope.nextMonth = function() {
      var nextMonth = ApplicationService.nextMonth($scope.searchDto.toDate);
      $scope.setRange(nextMonth);
      $scope.showBudgetedVsSpentReport();
    };
    $scope.setRange = function(date) {
      var fromDate = angular.copy(date);
      fromDate.setDate(1);
      $scope.searchDto.fromDate = fromDate;
      var toDate = angular.copy(date);
      var days = toDate.getDaysInMonth();
      toDate.setDate(days);
      console.info("from: " + fromDate);
      console.info("to: " + toDate);
      $scope.searchDto.toDate = toDate;      
    };
}]);
