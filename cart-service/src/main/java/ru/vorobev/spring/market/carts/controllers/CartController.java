package ru.vorobev.spring.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.spring.market.api.CartDto;
import ru.vorobev.spring.market.api.StringResponse;
import ru.vorobev.spring.market.carts.converters.CartConverter;
import ru.vorobev.spring.market.carts.services.CartService;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_uuid")
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}/merge/{username}")
    public void mergeGuestCart(@PathVariable String uuid, @PathVariable String username) {
        cartService.mergeGuestCartIntoUser(username, uuid);
    }

    @GetMapping("/{uuid}")
    public CartDto getCurrentCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        String targetUuid = getCartUuid(username, uuid);
        return cartConverter.entityToDto(cartService.getCurrentCart(targetUuid));
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addProductToCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.addProduct(targetUuid, id);
    }

    @GetMapping("/{uuid}/increment/{id}")
    public void incrementProductInCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.incrementProduct(targetUuid, id);
    }

    @GetMapping("/{uuid}/decrement/{id}")
    public void decrementProductInCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.decrementProduct(targetUuid, id);
    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.clearCart(targetUuid);
    }

    @GetMapping("/{uuid}/delete/{id}")
    public void deleteProductFromCart(@RequestHeader(name = "username", required = false) String username, @PathVariable String uuid, @PathVariable Long id) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.deleteProductById(targetUuid, id);
    }

    //По-хорошему бы вынести куда-нить в фильтры
    private String getCartUuid (String username, String uuid) {
        if (username != null) {
            return username;
        }
        return uuid;
    }
}
