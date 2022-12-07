package ru.vorobev.spring.market.api;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Модель списка категорий продуктов")
public class CategoryDto {
    @Schema(description = "Id категории", required = true, example = "1")
    private Long id;
    @Schema(description = "Название категории", required = true, example = "Еда")
    private String title;
    @Schema(description = "Список продуктов категории")
    private List<ProductDto> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
