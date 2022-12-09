package ru.vorobev.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobev.spring.market.api.CartDto;
import ru.vorobev.spring.market.api.OrderData;
import ru.vorobev.spring.market.api.ResourceNotFoundException;
import ru.vorobev.spring.market.core.entities.Order;
import ru.vorobev.spring.market.core.entities.OrderItem;
import ru.vorobev.spring.market.core.integrations.CartServiceIntegration;
import ru.vorobev.spring.market.core.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartServiceIntegration cartServiceIntegration;

    @Transactional
    public Order createOrder(String username, OrderData orderData) {
        CartDto cartDto = cartServiceIntegration.getCurrentCart(username);
        String orderAddress = null;
        String orderPhone = null;
        if (orderData != null) {
            orderAddress = orderData.getAddress();
            orderPhone = orderData.getPhone();
        }
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItems(cartDto.getItems().stream()
                .map(cartItem -> new OrderItem(
                                productService.findById(cartItem.getProductId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                "Формирование заказа: Продукт с id = : " + cartItem.getProductId() + " не найден")),
                                order,
                                cartItem.getQuantity(),
                                cartItem.getPricePerProduct(),
                                cartItem.getPrice()
                        )
                ).collect(Collectors.toList()));
        order.setAddress(orderAddress);
        order.setPhone(orderPhone);

        orderRepository.save(order);

        cartServiceIntegration.clearCurrentCart(username);

        return order;
    }

    public List<Order> findByUsername(String username) {
        return orderRepository.findByUsername(username);
    }

    public Optional<Order> findById(Long orderId) {
        return  orderRepository.findById(orderId);
    }
}
