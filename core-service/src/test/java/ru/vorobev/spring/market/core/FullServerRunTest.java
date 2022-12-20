package ru.vorobev.spring.market.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.vorobev.spring.market.api.PageDto;
import ru.vorobev.spring.market.api.ProductDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Поднимает полностью весь бэк микросервиса
@ActiveProfiles("test")     // задействует application-test.yaml
public class FullServerRunTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void fullRestTest() {
        PageDto<ProductDto> productPage = restTemplate.getForObject("/api/v1/products", PageDto.class);
        Assertions.assertNotNull(productPage);
        Assertions.assertFalse(productPage.getItems().isEmpty());
    }
}
