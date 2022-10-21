package ru.vorobev.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.dtos.OrderData;
import ru.vorobev.spring.market.entities.Order;
import ru.vorobev.spring.market.entities.OrderItem;
import ru.vorobev.spring.market.entities.User;
import ru.vorobev.spring.market.exceptions.ResourceNotFoundException;
import ru.vorobev.spring.market.models.Cart;
import ru.vorobev.spring.market.repositories.OrderItemRepository;
import ru.vorobev.spring.market.repositories.OrderRepository;

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
