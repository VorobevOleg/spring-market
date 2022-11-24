package ru.vorobev.spring.market.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vorobev.spring.market.api.CartDto;
import ru.vorobev.spring.market.api.CartItemDto;
import ru.vorobev.spring.market.api.OrderData;
import ru.vorobev.spring.market.core.entities.Category;
import ru.vorobev.spring.market.core.entities.Order;
import ru.vorobev.spring.market.core.entities.Product;
import ru.vorobev.spring.market.core.integrations.CartServiceIntegration;
import ru.vorobev.spring.market.core.repositories.OrderRepository;
import ru.vorobev.spring.market.core.services.OrderService;
import ru.vorobev.spring.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
//@SpringBootTest(classes = OrderService.class)     // можно запускать только какие-то определенные бины (так быстрее)
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ProductService productService;
    @MockBean
    private CartServiceIntegration cartServiceIntegration;

    @Test
    public void createOrderTest() {
        CartDto cartDto = new CartDto();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(1234L);
        cartItemDto.setProductTitle("Banana");
        cartItemDto.setPricePerProduct(BigDecimal.valueOf(70));
        cartItemDto.setQuantity(3);
        cartItemDto.setPrice(BigDecimal.valueOf(210));

        cartDto.setTotalPrice(BigDecimal.valueOf(210));
        cartDto.setItems(List.of(cartItemDto));

        Mockito.doReturn(cartDto).when(cartServiceIntegration).getCurrentCart();

        OrderData orderData = new OrderData();
        orderData.setAddress("Street 111");
        orderData.setPhone("8-000-555");

        Category category = new Category();
        category.setId(1L);
        category.setTitle("Food");

        Product product = new Product();
        product.setId(1234L);
        product.setTitle("Banana");
        product.setCategory(category);
        product.setPrice(BigDecimal.valueOf(70));

        Mockito.doReturn(Optional.of(product)).when(productService).findById(1234L);

        Order order = orderService.createOrder("Bob", orderData);
        Assertions.assertEquals(BigDecimal.valueOf(210), order.getTotalPrice(), "Проверка итогового 'Total price' в заказе");

        // проверка того, что у orderRepository метод save вызван только 1 раз с любым аргументом
        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.any());

    }
}
