package ru.vorobev.spring.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.spring.market.api.CategoryDto;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.api.ResourceNotFoundException;
import ru.vorobev.spring.market.core.converters.CategoryConverter;
import ru.vorobev.spring.market.core.converters.ProductConverter;
import ru.vorobev.spring.market.core.entities.Category;
import ru.vorobev.spring.market.core.entities.Product;
import ru.vorobev.spring.market.core.services.CategoryService;
import ru.vorobev.spring.market.core.services.ProductService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @GetMapping
    public List<ProductDto> findProducts(@RequestParam(required = false) Map<String,String> filterParams) { //TODO: в дальнейшем нужно возвращать page
        if (filterParams.isEmpty()) {
            return productService.findAll().stream()
                    .map(productConverter::entityToDto)
                    .collect(Collectors.toList());
        } else {
            return productService.findByFilters(filterParams).stream()
                    .map(productConverter::entityToDto)
                    .collect(Collectors.toList());
        }
    }


//    Один из вариантов обработки некорректных запросов (его минус - громоздкий)
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> findProduct(@PathVariable Long id) {
//        Optional<Product> product = productService.findById(id);
//        if (!product.isPresent()) {
//            ResponseEntity<AppError> err = new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
//                    "Продукт с id = " + id + " не найден"), HttpStatus.NOT_FOUND);
//            return err;
//        }
//        return new ResponseEntity<>(product.get(),HttpStatus.OK);
//    }


    @GetMapping("/{id}")
    public ProductDto findProduct(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Продукт с id = " + id + " не найден"));
        return productConverter.entityToDto(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProduct(@RequestBody (required = false) ProductDto productDto) {
        productService.createNewProduct(productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/categories")
    public List<CategoryDto> findCategories() {
        return categoryService.findAll().stream()
                .map(categoryConverter::entityToDtoWithoutProducts)
                .collect(Collectors.toList());
    }
}
