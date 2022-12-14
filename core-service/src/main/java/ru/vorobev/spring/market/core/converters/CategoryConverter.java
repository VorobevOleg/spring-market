package ru.vorobev.spring.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vorobev.spring.market.api.CategoryDto;
import ru.vorobev.spring.market.core.entities.Category;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryConverter {
    private final ProductConverter productConverter;

    public CategoryDto entityToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        categoryDto.setProducts(category.getProducts().stream()
                .map(productConverter::entityToDto)
                .collect(Collectors.toList()));
        return categoryDto;
    }

    public CategoryDto entityToDtoWithoutProducts(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());
        return categoryDto;
    }

}
