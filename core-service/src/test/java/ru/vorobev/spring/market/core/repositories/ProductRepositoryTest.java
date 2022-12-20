package ru.vorobev.spring.market.core.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.vorobev.spring.market.api.ResourceNotFoundException;
import ru.vorobev.spring.market.core.entities.Category;
import ru.vorobev.spring.market.core.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@DataJpaTest                // все операции с БД по завершении каждого отдельного теста будут отменены
@ActiveProfiles("test")     // задействует application-test.yaml
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    //  Нет необходимоста тестировать стандартные CRUD операции, тут это для примера
    @Test
    public void productRepositorySaveTest() {
        Category category = categoryRepository.findByTitle("Food")
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Product product = new Product();
        product.setId(123L);
        product.setTitle("Orange");
        product.setCategory(category);
        product.setPrice(BigDecimal.valueOf(710));
        productRepository.save(product);

        List<Product> productList = productRepository.findAll();
        Assertions.assertEquals(11, productList.size());
        Assertions.assertEquals("Orange", productList.get(10).getTitle());
    }

    @Test
    public void initDBTest() {
        List<Product> productList = productRepository.findAll();
        Assertions.assertEquals(10, productList.size());
    }
}