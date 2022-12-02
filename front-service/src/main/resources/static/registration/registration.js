angular.module('market').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.registerNewUser = function () {
        $http.post(contextPath + 'registration', $scope.newUser).then(function (response) {
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.springMarketUser = {username: $scope.newUser.username, token: response.data.token};
                $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.springMarketGuestCartId + '/merge/' + $scope.newUser.username)
                    .then(function (response) {
                    });
            }
            $scope.newUser = null;
            $location.path("/");
        });
    };

});