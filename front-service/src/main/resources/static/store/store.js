angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';
    const cartContextPath = 'http://localhost:5555/cart/';

    $scope.loadProducts = function () {
        $http.get(contextPath + 'api/v1/products').then(function (response) {
            $scope.productsList = response.data;
        });
    };

    $scope.loadProductsWithFilters = function () {
        $http.get(contextPath + 'api/v1/products',
            {params: {filterTitle: $scope.productFilters.title,
                    filterMin: $scope.productFilters.min,
                    filterMax: $scope.productFilters.max
                    }
            }).then(function (response) {
                $scope.productsList = response.data;
            });
    };

    $scope.showProductInfo = function (productId) {
        $http.get(contextPath + 'api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    };

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + 'api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    };

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

    $scope.addToCart = function (productId) {
        $http.get(cartContextPath + 'api/v1/cart/' + $localStorage.springMarketGuestCartId + '/add/' + productId).then(function (response) {});
    };

    $scope.loadProducts();
    $scope.loadCategories();
});