package ru.vorobev.spring.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vorobev.spring.market.api.CartDto;
import ru.vorobev.spring.market.api.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public CartDto getCurrentCart() {
        return cartServiceWebClient.get()
                .uri("/api/v1/cart")
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Корзина не найдена"))
                )
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clearCurrentCart() {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/clear")
                .retrieve()
                .toBodilessEntity()
                .block();
    }


//    Синхронный вариант через RestTemplate:
//
//    private final RestTemplate restTemplate;
//
//    @Value("${services-urls.cart}")
//    private String cartServiceUrl;
//
//    public Optional<CartDto> getCurrentCart() {
//        return Optional.ofNullable(restTemplate.getForObject(cartServiceUrl, CartDto.class));
//    }
//
//    public void clearCurrentCart() {
//        restTemplate.getForObject(cartServiceUrl + "clear", String.class);
//    }
}
