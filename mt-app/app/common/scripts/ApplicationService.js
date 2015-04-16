'use strict';
angular.module('mt-app')
.service('ApplicationService',['$rootScope', 'dialogs', function(rootScope, dialogs) {

  Date.isLeapYear = function (year) {
      return (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0));
  };

  Date.getDaysInMonth = function (year, month) {
      return [31, (Date.isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month];
  };

  Date.prototype.isLeapYear = function () {
      return Date.isLeapYear(this.getFullYear());
  };

  Date.prototype.getDaysInMonth = function () {
      return Date.getDaysInMonth(this.getFullYear(), this.getMonth());
  };

  Date.prototype.addMonths = function (value) {
      this.setDate(1);
      this.setMonth(this.getMonth() + value);
      return this;
  };
  // Date.prototype.getMonth = function () {
  //     return this.getMonth();
  // };
  // Date.prototype.setMonth = function (value) {
  //     return this.setMonth(value);
  // };
  Date.prototype.addDays = function (value) {
      this.setDate(this.getDate() + value);
      return this;
  };
    this.confirm = function(data, confirmed, declined) {
    	if (!data)
    		return false;
    	var dlg = dialogs.confirm(data.title, data.message);
		dlg.result.then(function(btn){
            confirmed();
        },function(btn){
            declined();
        });
    };

    this.getDeleteData=function(msg) {
    	var data = {};
        data.title = "Confirm Delete";
        if (!msg) {
            msg = "it";
        }
        data.message = "Do you want to delete " + msg + "?";
        return data;
    };

    this.prevMonth = function(date) {
      if (date) {
        date = date.addMonths(-1);
      }
      return date;
    };
    this.nextMonth = function(date) {
      if (date) {
        date = date.addMonths(1);
      }
      return date;
    };
    this.updateCurrentMonth = function(dateLong) {
      var date = new Date(dateLong);
      console.info(date);
      if (date) {
        var today = new Date();
        var m = today.getMonth();
        console.info("today.getMonth() " + m);
        date.setMonth(m);
      }
      return date;
    };
    this.isCurrentMonth = function(date) {
      if (date) {
        var today = new Date();
        var todayText = today.getMonth() + "-" + today.getYear();
        var dateText = date.getMonth() + "-" + date.getYear();
        console.info("isCurrentMonth " + todayText + " " + dateText);

        return todayText == dateText;
      }
      return false;
    };
}]);
