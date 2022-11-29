angular.module('market').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/cart/';
    const coreContextPath = 'http://localhost:5555/core/';

    $scope.loadCart = function () {
        $http.get(contextPath + 'api/v1/cart').then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.clearCart = function () {
        $http.get(contextPath + 'api/v1/cart/clear').then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.deleteProductFromCart = function (productId) {
        $http.get(contextPath + 'api/v1/cart/delete/' + productId).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.incrementProductInCart = function (productId) {
        $http.get(contextPath + 'api/v1/cart/increment/' + productId).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.decrementProductInCart = function (productId) {
        $http.get(contextPath + 'api/v1/cart/decrement/' + productId).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.placeOrder = function () {
        $http.post(coreContextPath + 'api/v1/orders', $scope.orderData).then(function (response) {
            alert("Заказ оформлен");
            $scope.orderData = null;
            $scope.clearCart();
        });
    };

    $scope.loadCart();
});