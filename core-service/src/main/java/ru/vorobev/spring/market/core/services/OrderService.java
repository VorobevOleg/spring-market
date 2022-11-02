package ru.vorobev.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobev.spring.market.core.dtos.OrderData;
import ru.vorobev.spring.market.core.entities.Order;
import ru.vorobev.spring.market.core.entities.OrderItem;
import ru.vorobev.spring.market.core.entities.User;
import ru.vorobev.spring.market.core.exceptions.ResourceNotFoundException;
import ru.vorobev.spring.market.core.models.Cart;
import ru.vorobev.spring.market.core.repositories.OrderItemRepository;
import ru.vorobev.spring.market.core.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final CartService cartService;

    @Transactional
    public void createOrder(User user, OrderData orderData) {
        Cart cart = cartService.getCurrentCart();
        String orderAddress = null;
        String orderPhone = null;
        if (orderData != null) {
            orderAddress = orderData.getAddress();
            orderPhone = orderData.getPhone();
        }
        Order order = new Order(
                null,
                user,
                new ArrayList<OrderItem>(),
                orderAddress,
                orderPhone,
                cart.getTotalPrice(),
                LocalDateTime.now(),
                LocalDateTime.now());

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> new OrderItem(
                                null,
                                productService.findById(cartItem.getProductId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                        "Формирование заказа: Продукт с id = : " + cartItem.getProductId() + " не найден")),
                                order,
                                cartItem.getQuantity(),
                                cartItem.getPricePerProduct(),
                                cartItem.getPrice(),
                                LocalDateTime.now(),
                                LocalDateTime.now()
                        )
                ).collect(Collectors.toList());

        order.setItems(orderItems);

        orderRepository.save(order);

        orderItemRepository.saveAll(orderItems);
    }
}
