package ru.vorobev.spring.market.dtos;

import lombok.Data;
import ru.vorobev.spring.market.entities.Product;

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

    public void add(Product product) { // TODO: Доработать в ДЗ
        items.add(new CartItem(product.getId(), product.getTitle(),1 , product.getPrice(), product.getPrice()));
        recalculate();
    }

    public void recalculate() {
        totalPrice = BigDecimal.ZERO;

        for (CartItem item: items) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }
}
