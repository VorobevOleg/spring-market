package ru.vorobev.spring.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.carts.integrations.ProductServiceIntegration;
import ru.vorobev.spring.market.carts.models.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;
    private Map<String, Cart> carts;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!carts.containsKey(targetUuid)) {
            carts.put(targetUuid, new Cart());
        }
        return  carts.get(targetUuid);
    }

    public void addProduct(String uuid, Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        getCurrentCart(uuid).add(product);
    }

    public void clearCart(String uuid) {
        getCurrentCart(uuid).clear();
    }

    public void deleteProductById(String uuid, Long productId) {
        getCurrentCart(uuid).delete(productId);
    }

    public void incrementProduct(String uuid, Long productId) { getCurrentCart(uuid).incrementQuantity(productId);}

    public void decrementProduct(String uuid, Long productId) {
        getCurrentCart(uuid).decrementQuantity(productId);
    }
}
