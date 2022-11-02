package ru.vorobev.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.spring.market.models.Cart;
import ru.vorobev.spring.market.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProduct(id);
    }

    @GetMapping("/decrement/{id}")
    public void decrementProductInCart(@PathVariable Long id) {
        cartService.decrementProduct(id);
    }

    @GetMapping("/clear")
    public void clearCart() {
        cartService.clearCart();
    }

    @GetMapping("/delete/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        cartService.deleteProductById(id);
    }
}
