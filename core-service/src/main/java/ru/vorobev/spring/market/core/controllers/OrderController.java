package ru.vorobev.spring.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.spring.market.api.OrderData;
import ru.vorobev.spring.market.api.OrderDto;
import ru.vorobev.spring.market.core.converters.OrderConverter;
import ru.vorobev.spring.market.core.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private  final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody(required = false) OrderData orderData) {
        orderService.createOrder(username, orderData);
    }

    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username) {
        return orderService.findByUsername(username).stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
