package ru.vorobev.spring.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.spring.market.core.dtos.OrderData;
import ru.vorobev.spring.market.core.entities.User;
import ru.vorobev.spring.market.core.services.OrderService;
import ru.vorobev.spring.market.core.services.UserService;
import ru.vorobev.spring.market.core.exceptions.ResourceNotFoundException;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal, @RequestBody(required = false) OrderData orderData) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с именем: " + principal.getName() + " не найден"));
        orderService.createOrder(user, orderData);
    }
}
