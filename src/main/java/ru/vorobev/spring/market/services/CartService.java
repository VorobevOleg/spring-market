package ru.vorobev.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.dtos.Cart;
import ru.vorobev.spring.market.entities.Product;
import ru.vorobev.spring.market.exceptions.ResourceNotFoundException;

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
    
//    public void deleteById(Long id) {
//        cartList.remove(id);
//    }
}
