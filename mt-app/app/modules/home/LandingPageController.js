'use strict';

angular.module('mt-app')
    .controller('LandingPageController', ['dialogs', '$rootScope', '$scope', '$state', '$location', 'UserService',
    function (dialogs, $rootScope, $scope, $state, $location, UserService) {
    console.info("LandingPageController");
    $scope.myInterval = 5000;
  $scope.slides = [
    { // Finance.png
      image: 'common/images/Carousel/Money.jpg',
      logo: 'common/images/128/Money.png',
      textColor: '#FFF',
      caption: 'Track your Money',
      heading: 'Money… Money…. Where are you?',
      description: 'You work hard, spend, invest, try to save, but it never seems to add up. Sounds familiar?.... No Problem! Track your income and expenses here.',
    },
    {
      image: 'common/images/Carousel/PlanBudget.jpg',
      textColor: '#FFF',
      caption: 'Plan your Budget'
    },
    { //fin-report.png
      image: 'common/images/Carousel/Reminder.jpg',
      textColor: '#252530',
      caption: 'Get Reminders'
    },
    {
      image: 'common/images/Carousel/Statement.jpg',
      textColor: '#FFF',
      caption: 'Import Statements (Bank & Personal)'
    },
    {
      image: 'common/images/Carousel/OneView.jpg',
      textColor: '#252530',
      caption: 'See all your Accounts in a singal View'
    },
    {
      image: 'common/images/Carousel/Category.jpg',
      textColor: '#252530',
      caption: 'Group your own Category Hierarchy'
    },
    {
      image: 'common/images/Carousel/Report.jpg',
      textColor: '#FFF',
      caption: 'Analyze Visual Reports'
    }
  ];
}]);