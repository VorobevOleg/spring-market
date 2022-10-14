package ru.vorobev.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.entities.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final HashMap<Long, Product> cartList;

    public List<Product> findAll() {
        return new ArrayList<Product>(cartList.values());
    }

    public void addProduct(Product product) {
        Long id = product.getId();
        cartList.put(id, product);
    }

    public void deleteById(Long id) {
        cartList.remove(id);
    }
}
