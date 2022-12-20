package ru.vorobev.spring.market.core.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vorobev.spring.market.api.PageDto;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc   // Эмуляция бэка (быстрее, чем поднять настоящий)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    void findProducts() throws Exception {
        ProductDto p1 = new ProductDto();
        p1.setTitle("Banana");
        p1.setCategoryTitle("Food");
        p1.setPrice(new BigDecimal(150));
        ProductDto p2 = new ProductDto();
        p2.setTitle("Milk");
        p2.setCategoryTitle("Food");
        p2.setPrice(new BigDecimal(100));

        PageDto<ProductDto> productPage = new PageDto<>();
        productPage.setItems(new ArrayList<>(List.of(p1,p2)));

        Map<String, String> filterParams = new HashMap<>();

        given(productService.findAll(filterParams,0)).willReturn(productPage);

        mockMvc.perform(
                get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.items[0].title").value(productPage.getItems().get(0).getTitle()));
    }

//    @Test
//    void findProduct() {
//    }
//
//    @Test
//    void createNewProduct() {
//    }
//
//    @Test
//    void deleteProductById() {
//    }
//
//    @Test
//    void findCategories() {
//    }
}