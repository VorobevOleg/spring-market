package ru.vorobev.spring.market.carts.models;

import lombok.Data;
import ru.vorobev.spring.market.api.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
        this.totalPrice = BigDecimal.ZERO;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void add(ProductDto product) {
        if (!containsInCart(product)) {
            items.add(new CartItem(product.getId(), product.getTitle(),1 , product.getPrice(), product.getPrice()));
        } else {
            incrementQuantity(product);
        }
        recalculate();
    }

    public void clear() {
        items.clear();
        recalculate();
    }

    public void delete(Long productId) {

//        Вместо этого:
//
//        for (CartItem item: items) {
//            if (productId.equals(item.getProductId())) {
//                items.remove(item);
//                break;
//            }
//        }
//
//        Можно использвать это:

        items.removeIf(item -> productId.equals(item.getProductId()));

        recalculate();
    }

    private boolean containsInCart(ProductDto product) {
        Long id = product.getId();
        for (CartItem item: items) {
            if (id.equals(item.getProductId())) {
                return true;
            }
        }
        return false;
    }

    public void incrementQuantity(ProductDto product) {
        Long id = product.getId();
        for (CartItem item: items) {
            if (id.equals(item.getProductId())) {
                item.incrementQuantity();
            }
        }
    }

    public void decrementQuantity(Long productId) {
        for (CartItem item: items) {
            if (productId.equals(item.getProductId())) {
                item.decrementQuantity();
            }
        }
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        for (CartItem item: items) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }


}
