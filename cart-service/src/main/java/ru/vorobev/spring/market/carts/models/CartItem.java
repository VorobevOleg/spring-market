package ru.vorobev.spring.market.carts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;

    public void incrementQuantity() {
        quantity ++;
        recalculate();
    }

    public void recalculate() {
        price = pricePerProduct.multiply(new BigDecimal(quantity));
    }

    public void decrementQuantity() {
        if (quantity > 0) {
            quantity --;
            recalculate();
        }
    }

}
