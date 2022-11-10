package ru.vorobev.spring.market.carts.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.api.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;

    public ProductDto getProductById(Long id) {
        return productServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден в продуктовом МС"))
                )
                .bodyToMono(ProductDto.class)
                .block();
    }




//    Варианты:

//    public void clearUserCart(String username) {
//        cartServiceWebClient.get()
//                .uri("/api/v1/cart/0/clear")
//                .header("username", username)
//                .rertrive()
//                .toBodilessEntity()
//                .block(); //  это если нужно дождаться ответа,если асинхронная обработка, то использовать штуки Completable Future
//    }

//    public CartDto getUserCart(String username) {
//        CartDto cart = cartSericeWebClient.get()
//                .uri("/api/v1/cart/0")
//                .header("username", username)
//                //  .bodyValue(body)    // for POST
//                .retrieve()
//                .onStatus(
//                        httpStatus -> httpStatus.is4xxClientError(),    //  HttpStatus::is4xxClientError
//                        clientResonse -> clientResonse.bodyToMono(CartServiceAppError.class).map(
//                                body -> {
//                                    if (body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_NOT_FOUND.name())) {
//                                        return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина не найдена");
//                                    }
//                                    if (body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_IS_BROKEN.name())) {
//                                        return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина сломана");
//                                    }
//                                    return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: причина неизвестна");
//                                }
//                        )
//                )
//                .onStatus(HttpStatus::is4xxClientError,clientResponse -> Mono.error(new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин")))
//                .onStatus(HttpStatus::is5xxServerError,clientResponse -> Mono.error(new CartServiceIntegrationException("Сервис корзин сломался")))
//                .bodyToMono(CartDto.class)    //  Mono - для получения 1 объекта, а Flux - для пачки объектов
//                .block();
//        return cart;
//    }
}
