package ru.vorobev.spring.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.vorobev.spring.market.api.CartDto;
import ru.vorobev.spring.market.api.ProductDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    public Optional<CartDto> getCurrentCart() {
        return Optional.ofNullable(restTemplate.getForObject("http://localhost:8190/market-carts/api/v1/cart", CartDto.class));
    }
}
