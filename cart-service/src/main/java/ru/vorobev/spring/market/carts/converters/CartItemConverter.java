package ru.vorobev.spring.market.carts.converters;

import org.springframework.stereotype.Component;
import ru.vorobev.spring.market.api.CartItemDto;
import ru.vorobev.spring.market.carts.models.CartItem;

@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setPrice(cartItem.getPrice());
        cartItemDto.setPricePerProduct(cartItem.getPricePerProduct());
        cartItemDto.setProductId(cartItem.getProductId());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setProductTitle(cartItem.getProductTitle());
        return cartItemDto;
    }
}
