angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
    };

    $scope.clearUser = function () {
        delete $localStorage.springMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        return $localStorage.springMarketUser;
    };

    if ($localStorage.springMarketUser) {
        try {
            let jwt = $localStorage.springMarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token s expired!!!");
                delete $localStorage.springMarketUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {
        }
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springMarketUser.token;
    }

    $scope.loadProducts = function () {
        $http.get('http://localhost:5555/core/api/v1/products')
            .then(function (response) {
                $scope.productsList = response.data;
            });
    };

    $scope.loadProductsWithFilters = function () {
        $http.get('http://localhost:5555/core/api/v1/products',
            {params: {filterTitle: $scope.productFilters.title,
                        filterMin: $scope.productFilters.min,
                        filterMax: $scope.productFilters.max}})
                .then(function (response) {
                    $scope.productsList = response.data;
                });
    };

    $scope.showProductInfo = function (productId) {
        $http.get('http://localhost:5555/core/api/v1/products/' + productId)
            .then(function (response) {
                alert(response.data.title);
            });
    };

    $scope.deleteProduct = function (productId) {
        $http.delete('http://localhost:5555/core/api/v1/products/' + productId)
            .then(function (response) {
                $scope.loadProducts();
            });
    };

    $scope.createNewProduct = function () {
        $http.post('http://localhost:5555/core/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.loadProducts();
            });
    };

    $scope.loadCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.loadCategories = function () {
        $http.get('http://localhost:5555/core/api/v1/products/categories')
            .then(function (response) {
                $scope.categories = response.data;
            });
    };

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/add/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.clearCart = function () {
        $http.get('http://localhost:5555/cart/api/v1/cart/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.incrementProductInCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/increment/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.decrementProductInCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/decrement/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.deleteProductFromCart = function (productId) {
        $http.get('http://localhost:5555/cart/api/v1/cart/delete/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.placeOrder = function () {
        $http.post('http://localhost:5555/core/api/v1/orders', $scope.orderData)
            .then(function (response) {
                alert("Заказ оформлен");
                $scope.orderData = null;
                $scope.clearCart();
            });
    };

    $scope.loadProducts();
    $scope.loadCart();
    $scope.loadCategories();
});