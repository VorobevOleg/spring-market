package ru.vorobev.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.core.models.Cart;
import ru.vorobev.spring.market.core.entities.Product;
import ru.vorobev.spring.market.core.exceptions.ResourceNotFoundException;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return  tempCart;
    }

    public void addProduct(Long productId) {
        Product product = productService.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Не удалось добавить продукт с id = " + productId + ". Продукт не найден."));
        tempCart.add(product);
    }

    public void clearCart() {
        tempCart.clear();
    }
    public void deleteProductById(Long productId) {
        tempCart.delete(productId);
    }

    public void decrementProduct(Long productId) {
        tempCart.decrementQuantity(productId);
    }
}
