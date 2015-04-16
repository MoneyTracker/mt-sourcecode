'use strict';

angular.module('mt-app')
    .controller('LandingPageController', ['dialogs', '$rootScope', '$scope', '$state', '$location', 'UserService',
    function (dialogs, $rootScope, $scope, $state, $location, UserService) {
    console.info("LandingPageController");
    $scope.myInterval = 5000;
  $scope.slides = [
    {
      image: '/common/images/Carousel/Finance.png',
      logo: '/common/images/128/Money.png',
      caption: 'Track your Money',
      heading: 'Money… Money…. Where are you?',
      description: 'You work hard, spend, invest, try to save, but it never seems to add up. Sounds familiar?.... No Problem! Track your income and expenses here.',
    },
    {
      image: '/common/images/Carousel/Budget3.png',
      caption: 'Plan your Budget'
    },
    {
      image: '/common/images/Carousel/fin-report.png',
      caption: 'Get Reminders'
    },
    {
      image: '/common/images/Carousel/Budget3.png',
      caption: 'Import Excel Sheets'
    },
    {
      image: '/common/images/Carousel/Accounts.jpg',
      caption: 'Manage Accounts'
    },
    {
      image: '/common/images/Carousel/plan.jpg',
      caption: 'Manage Categories'
    },
    {
      image: '/common/images/Carousel/fin-report.png',
      caption: 'See Reports'
    }
  ];
}]);
