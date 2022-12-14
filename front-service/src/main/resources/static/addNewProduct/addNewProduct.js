angular.module('market').controller('addNewProductController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.createNewProduct = function () {
        $http.post(contextPath + 'api/v1/products', $scope.newProduct).then(function (response) {
            $scope.newProduct = null;
            $scope.loadProducts();
        });
    };

    $scope.loadCategories = function () {
        $http.get(contextPath + 'api/v1/products/categories').then(function (response) {
            $scope.categories = response.data;
        });
    };

    $scope.loadCategories();
});