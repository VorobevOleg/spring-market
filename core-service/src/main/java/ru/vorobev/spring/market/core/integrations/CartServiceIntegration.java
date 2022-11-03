package ru.vorobev.spring.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.vorobev.spring.market.api.CartDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${services-urls.cart}")
    private String cartServiceUrl;

    public Optional<CartDto> getCurrentCart() {
        return Optional.ofNullable(restTemplate.getForObject(cartServiceUrl, CartDto.class));
    }

    public void clearCurrentCart() {
        restTemplate.getForObject(cartServiceUrl + "clear", String.class);
    }
}
