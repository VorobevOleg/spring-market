angular.module('market', []).controller('indexController', function ($scope, $http) {

    $scope.loadProducts = function () {
        $http.get('http://localhost:8189/market/api/v1/products')
            .then(function (response) {
                $scope.productsList = response.data;
            });
    }

    $scope.showProductInfo = function (productId) {
        $http.get('http://localhost:8189/market/api/v1/products/' + productId)
            .then(function (response) {
                alert(response.data.title);
            });
    }

    $scope.deleteProduct = function (productId) {
        $http.delete('http://localhost:8189/market/api/v1/products/' + productId)
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.createNewProduct = function () {
        $http.post('http://localhost:8189/market/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.loadProducts();
            });
    }

    $scope.loadProductsToCart = function () {
        $http.get('http://localhost:8189/market/api/v1/cart')
            .then(function (response) {
                $scope.productsListCart = response.data;
            });
    }

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:8189/market/api/v1/cart/add/' + productId)
            .then(function (response) {
                $scope.loadProductsToCart();
            });
    }

    $scope.deleteProductFromCart = function (productId) {
        $http.get('http://localhost:8189/market/api/v1/cart/delete/' + productId)
            .then(function (response) {
                $scope.loadProductsToCart();
            });
    }

    $scope.loadProducts();
    $scope.loadProductsToCart();
});