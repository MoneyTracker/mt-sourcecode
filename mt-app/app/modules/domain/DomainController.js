'use strict';

angular.module('mt-app')
    .controller('DomainController', ['$rootScope', '$scope', '$state', '$filter', '$window', 'ngTableParams','ApplicationService', 'DomainService','ACTION_INDEX',
    function ($rootScope, $scope, $state, $filter, $window, ngTableParams, ApplicationService, DomainService, ACTION_INDEX) {
    console.info("DomainController");
    $scope.categoryTree = [];
    $scope.$types = [{code: 'E', name: 'Expense', typeClass: 'expTexture'},
    {code: 'I', name: 'Income', typeClass: 'incTexture'},
    {code: 'T', name: 'Transfer', typeClass: 'tnfrTexture'}];
    $scope.$type = $scope.$types[0].code;
    $scope.searchDto = {
        'page': { 'pageNumber': 0, 'pageSize': 10}, 'transactionType': $scope.$type
    };
    $scope.changeType=function(type) {
        $scope.$type = type.code;
        if ($scope.$type == 'E') {
          $scope.categoryTree = $scope.expCategories;
        } else if ($scope.$type == 'I') {
          $scope.categoryTree = $scope.incCategories;
        } else if ($scope.$type == 'T') {
            $scope.categoryTree = $scope.transferCategories;
        }
        $scope.searchDto.transactionType = $scope.$type;
        $scope.catTableParams.reload();
    };
    $scope.newAccount = {
      'name': 'New Account',
      'balance': 0,
      'accountTypeCode': 'C',
      'action': {'actionIndex' : ACTION_INDEX.NEW}
    };
    $scope.newCategory = {
      'name': 'New Category',
      'action': {'actionIndex' : ACTION_INDEX.NEW}
    };

    $scope.selectedAccount = {};
    $scope.init = function () {
        DomainService.loadPeriods($scope);
        DomainService.loadAccountTypes($scope);
        $scope.initAccountsTable();
        $scope.initCategoriesTable();
    };
    $scope.initAccountsTable = function() {
      $scope.acctTableParams = new ngTableParams({
          page: $scope.searchDto.page.pageNumber,            // show first page
          count: $scope.searchDto.page.pageSize,           // count per page
          sorting: {
              name: 'asc'     // initial sorting
          }
      }, {
          total: $scope.searchDto.page.totalRecords, // length of data
          getData: function($defer, params) {
              var page = params.page();
              var count = params.count();
              $scope.searchDto.page.pageNumber = page;
              $scope.searchDto.page.pageSize = count;
              var orderBy = params.orderBy();
              var order = "DESC";
              if (orderBy != undefined && orderBy.length > 0) {
                  var sign = orderBy[0].substring(0, 1);
                  orderBy = orderBy[0].substring(1);
                  if (sign == '+') {
                      order = "ASC";
                  }
                  $scope.searchDto.orderByList = [];
                  var orderBySpec = { "propertyName" : orderBy,
                  "operation": order};
                  $scope.searchDto.orderByList.push(orderBySpec);
              }
              var promise = DomainService.listAccounts($scope.searchDto);
              promise.then(
                  function (data) {
                    $scope.accountsInitialized = true;
                    $scope.accounts = data;
                  },
                  function (reason) {
                      console.log('Failed: ' + reason);
                  }
              );
              $defer.resolve($scope.accounts);
          }
      });
    };
    $scope.initCategoriesTable = function() {
      $scope.catTableParams = new ngTableParams({
          page: $scope.searchDto.page.pageNumber,            // show first page
          count: $scope.searchDto.page.pageSize,           // count per page
          sorting: {
              name: 'asc'     // initial sorting
          }
      }, {
          total: $scope.searchDto.page.totalRecords, // length of data
          getData: function($defer, params) {
              var page = params.page();
              var count = params.count();
              $scope.searchDto.page.pageNumber = page;
              $scope.searchDto.page.pageSize = count;
              var orderBy = params.orderBy();
              var order = "DESC";
              if (orderBy != undefined && orderBy.length > 0) {
                  var sign = orderBy[0].substring(0, 1);
                  orderBy = orderBy[0].substring(1);
                  if (sign == '+') {
                      order = "ASC";
                  }
                  $scope.searchDto.orderByList = [];
                  var orderBySpec = { "propertyName" : orderBy,
                  "operation": order};
                  $scope.searchDto.orderByList.push(orderBySpec);
              }
              var promise = DomainService.listCategoryTree($scope.searchDto);
              promise.then(
                  function (data) {
                    $scope.categoriesInitialized = true;
                    if (data) {
                      $scope.categoryTree = data;  
                    }
                  },
                  function (reason) {
                      console.log('Failed: ' + reason);
                  }
              );
              $defer.resolve($scope.categoryTree);
          }
      });
    };
    $scope.loadCategoryTree =function() {
        var promise = DomainService.listCategoryTree($scope.searchDto);
        var thisService = this;
        promise.then(
            function (data) {
              $scope.categoryTree = data;
              if (data && data.length > 0) {
                DomainService.segregateWithTypes($scope, data);
                $scope.changeType($scope.$types[0]);
              }
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );
    };
    $scope.globalConfig={
        isPaginationEnabled:false,
        selectionMode:'single'
      };

    $scope.columnsConfig=[
        {label:'Name',map:'name'},
        {label:'Account Type',map:'accountType.name'},
        {label:'Balance',map:'balance'}
    ];

    $scope.footerDetail = function() {
        var footerText;
        var totalExp = 0;
        var totalInc = 0;
        angular.forEach($scope.transactions, function(t) {
            if (t.category.transactionTypeCode == 'E') {
                totalExp+=t.amount;
            } else if (t.category.transactionTypeCode == 'I') {
                totalInc+=t.amount;
            }
        });
        footerText = "Total Income: " + totalInc + ", Total Expenses: " + totalExp;
        return footerText;
    };
    $scope.footerDetailClass = function() {
        return defaultFooterDetail;
    };
    $scope.categoryChanged = function(c) {
        console.info("cat changed");
        console.info(c.id);
        if (c.action.actionIndex != ACTION_INDEX.NEW) {
            c.action.actionIndex = ACTION_INDEX.UPDATE;
        }
    };
    $scope.categoryTypeChanged = function(c) {
      if (c.transactionTypeCode) {
        var selected = $filter('filter')($scope.$types, {code: c.transactionTypeCode});
        var t = selected.length ? selected[0] : undefined;
        if (t) {
          c.transactionType = t;
        }
      }
      $scope.categoryChanged(c);
    };
    $scope.showAccount = function(t) {
        if(t.accountId && $scope.accounts.length) {
          var selected = $filter('filter')($scope.accounts, {id: t.accountId});
          console.dir(selected);
          return selected.length ? selected[0].name : 'Not set';
        } else {
          return t.account.name || 'Not set';
        }
    };
    $scope.setCategoryDefaultAccount = function(c) {
      console.info("setCategoryDefaultAccount");
      if (c.defaultAccountId) {
        var selected = $filter('filter')($scope.accounts, {id: c.defaultAccountId});
        var a = selected.length ? selected[0] : undefined;
        if (a) {
          c.defaultAccount = a;
        }
      }
        return a;
    };
    $scope.accountChanged = function(a) {
        console.info(a.name + " changed");
        if (a.action.actionIndex != ACTION_INDEX.NEW) {
            a.action.actionIndex = ACTION_INDEX.UPDATE;
        }
    };
    $scope.addAccount = function() {
        $scope.insertedAccount = angular.copy($scope.newAccount);
        if ($scope.accounts == undefined) {
            $scope.accounts = [];
        }
        $scope.accounts.unshift($scope.insertedAccount);
        $scope.insertedAccount.$$edit = true;
    };
    $scope.saveAccount = function(a) {
        var index = $scope.accounts.indexOf(a);
        console.info("saveAccount " + index);
        var accounts = [];
        accounts.push(a);
        var promise = DomainService.storeAccounts(accounts);
        promise.then(
            function (data) {
               $scope.originalAccount = undefined;
               $scope.accounts[index] = data[0];
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );

    };
    $scope.deleteAccount = function(a) {
        if (a && a.id) {
          var confirmedCallback = function() {
              console.info("deleting ...");
              var promise = DomainService.deleteAccount(a.id);
              promise.then(
                  function (data) {
                    $scope.removeAccountFromTable(a);
                  },
                  function (reason) {
                      console.log('Failed: ' + reason);
                  }
              );
          };
          var declinedCallback = function() {
              console.info("declined ...");
          };
          var data = ApplicationService.getDeleteData(a.name);
          console.dir(data);
          ApplicationService.confirm(data, confirmedCallback, declinedCallback);
        }
    };
    $scope.editAccount = function(a) {
        $scope.originalAccount = angular.copy(a);
        a.$$edit = true;
    };
    $scope.cancelAccount = function(a) {
        a.$$edit = false;
        var index = $scope.accounts.indexOf(a);
        if (a.id) {
            $scope.accounts[index] = $scope.originalAccount;
            $scope.originalAccount = undefined;
        } else {
            $scope.accounts.splice(index, 1);
        }
    };
    $scope.removeAccountFromTable=function(a) {
      var index = $scope.accounts.indexOf(a);
      if (index > -1) {
        $scope.accounts.splice(index, 1);
      }
    };
    $scope.saveCategory = function(c) {
        var categories = [];
        categories.push(c);
        var promise = DomainService.storeCategories(categories);
        promise.then(
            function (data) {
               $scope.originalCategory = undefined;
               $scope.resetCategory(c, data[0]);
            },
            function (reason) {
                console.log('Failed: ' + reason);
            }
        );
    };
    $scope.addCategory = function() {
        $scope.insertedCat = angular.copy($scope.newCategory);
        if (! $scope.categoryTree) {
            $scope.categoryTree = [];
        }
        $scope.insertedCat.transactionTypeCode = $scope.$type;
        $scope.categoryTree.unshift($scope.insertedCat);
        $scope.insertedCat.$$edit = true;
    };
    $scope.addChildCategory = function(c) {
      $scope.insertedCat = angular.copy($scope.newCategory);
      $scope.insertedCat.$$edit = true;
      $scope.insertedCat.transactionTypeCode = $scope.$type;
      c.$$hideRows = false;
      if (!c.children) {
        c.children = [];
      }
      c.children.push($scope.insertedCat);
      $scope.insertedCat.parentCategoryId = c.id;
    };
    $scope.editCategory = function(c) {
        $scope.originalCategory = angular.copy(c);
        c.$$edit = true;
    };
    $scope.cancelCategory = function(c) {
        c.$$edit = false;
        var index = $scope.categoryTree.indexOf(c);
        if (c.id) {
          $scope.resetCategory(c, $scope.originalCategory);
        } else {
          $scope.categoryTree.splice(index, 1);
        }
    };
    $scope.resetCategory=function(c, withCat) {
      var parentId = c.parentCategoryId;
      var index = -1;
      if (parentId) {
        var selected = $filter('filter')($scope.categoryTree, {id: parentId});
        var parent = selected[0];
        if (parent) {
          index = parent.children.indexOf(c);
          parent.children[index] = withCat;
        }
      } else {
        index = $scope.categoryTree.indexOf(c);
        $scope.categoryTree[index] = withCat;
      }
      withCat = null;
    };
    $scope.removeCategoryFromTable=function(c) {
      var index = $scope.categoryTree.indexOf(c);
      if (index > -1) {
        $scope.categoryTree.splice(index, 1);
      }
    };
}]);
