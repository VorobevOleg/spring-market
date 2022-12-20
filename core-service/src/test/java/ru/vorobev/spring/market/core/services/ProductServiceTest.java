package ru.vorobev.spring.market.core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.vorobev.spring.market.api.PageDto;
import ru.vorobev.spring.market.api.ProductDto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")     // задействует application-test.yaml
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    void findAllTest() {
        Map<String, String> filterParams = new HashMap<>();
        filterParams.put("filterTitle", "Cheese");
        filterParams.put("filterMin", "160");
        filterParams.put("filterMax", "180");

        PageDto<ProductDto> page = productService.findAll(filterParams, 0);
        Assertions.assertEquals(2, page.getItems().size());
    }

    @Test
    void createNewProductTest() {
        ProductDto productDto = new ProductDto();
        productDto.setTitle("Orange");
        productDto.setPrice(new BigDecimal(300));
        productDto.setCategoryTitle("Food");

        Map<String, String> filterParams = new HashMap<>();

        productService.createNewProduct(productDto);

        Assertions.assertEquals("Orange", productService.findAll(filterParams, 2).getItems().get(0).getTitle());
        Assertions.assertEquals("Food", productService.findAll(filterParams, 2).getItems().get(0).getCategoryTitle());
    }
}