angular.module('market').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/auth/';

    $scope.registerNewUser = function () {
        $http.post(contextPath + 'registration', $scope.newUser).then(function (response) {
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.springMarketUser = {username: $scope.newUser.username, token: response.data.token};
            }
            $scope.newUser = null;
            $location.path("/");
        });
    };

});