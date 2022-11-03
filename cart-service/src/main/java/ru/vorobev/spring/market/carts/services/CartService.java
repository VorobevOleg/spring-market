package ru.vorobev.spring.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.api.ResourceNotFoundException;
import ru.vorobev.spring.market.carts.integrations.ProductServiceIntegration;
import ru.vorobev.spring.market.carts.models.Cart;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return  tempCart;
    }

    public void addProduct(Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId).orElseThrow(
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
