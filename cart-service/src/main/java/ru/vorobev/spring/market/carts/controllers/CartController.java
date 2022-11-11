package ru.vorobev.spring.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.spring.market.api.CartDto;
import ru.vorobev.spring.market.carts.converters.CartConverter;
import ru.vorobev.spring.market.carts.services.CartService;



@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.entityToDto(cartService.getCurrentCart());
    }

    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProduct(id);
    }

    @GetMapping("/increment/{id}")
    public void incrementProductInCart(@PathVariable Long id) {
        cartService.incrementProduct(id);
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
