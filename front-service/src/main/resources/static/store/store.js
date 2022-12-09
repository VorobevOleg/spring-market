angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';
    const cartContextPath = 'http://localhost:5555/cart/';

    $scope.loadProducts = function (pageIndex = 1) {
        $http.get(contextPath + 'api/v1/products', {
             params: {
                 page: pageIndex,
                 filterTitle: $scope.productFilters ? $scope.productFilters.title : null,
                 filterMin: $scope.productFilters ? $scope.productFilters.min : null,
                 filterMax: $scope.productFilters ? $scope.productFilters.max : null
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.generatePagesList($scope.productsPage.totalPages);
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

    $scope.addToCart = function (productId) {
        $http.get(cartContextPath + 'api/v1/cart/' + $localStorage.springMarketGuestCartId + '/add/' + productId).then(function (response) {});
    };

    $scope.generatePagesList = function (totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }

    $scope.loadProducts(1);
});