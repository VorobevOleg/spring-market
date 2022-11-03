package ru.vorobev.spring.market.carts.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.vorobev.spring.market.api.ProductDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${services-urls.product}")
    private String productServiceUrl;

    public Optional<ProductDto> getProductById(Long id) {
        return Optional.ofNullable(restTemplate.getForObject(productServiceUrl + id, ProductDto.class));
    }
}
