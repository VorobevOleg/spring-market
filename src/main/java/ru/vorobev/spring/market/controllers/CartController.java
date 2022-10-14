package ru.vorobev.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.spring.market.entities.Product;
import ru.vorobev.spring.market.services.CartService;
import ru.vorobev.spring.market.services.PorductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final PorductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return cartService.findAll();
    }

    @GetMapping("/add/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToCart(@PathVariable Long id) {
        Product product = productService.findById(id).get();
        cartService.addProduct(product);
    }

    @GetMapping("/delete/{id}")
    public void deleteProductFromCart(@PathVariable Long id) {
        cartService.deleteById(id);
    }
}
