package ru.vorobev.spring.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.api.ResourceNotFoundException;
import ru.vorobev.spring.market.core.entities.Category;
import ru.vorobev.spring.market.core.entities.Product;
import ru.vorobev.spring.market.core.services.CategoryService;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;
    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }

    public Product dtoToEntity(ProductDto productDto) {
//        Product product = new Product();
//        product.setId(productDto.getId());
//        product.setTitle(productDto.getTitle());
//        product.setPrice(productDto.getPrice());
//        Category category = categoryService.findByTitle(productDto.getCategoryTitle())
//                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
//        product.setCategory(category);

//  Попробовал реализовать паттерн "Builder" на примере продукта

        return Product.builder()
                .id(productDto.getId())
                .title(productDto.getTitle())
                .category(categoryService.findByTitle(productDto.getCategoryTitle())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found")))
                .build();
    }
}
