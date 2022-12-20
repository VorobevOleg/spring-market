package ru.vorobev.spring.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.spring.market.api.*;
import ru.vorobev.spring.market.core.converters.CategoryConverter;
import ru.vorobev.spring.market.core.converters.ProductConverter;
import ru.vorobev.spring.market.core.entities.Product;
import ru.vorobev.spring.market.core.services.CategoryService;
import ru.vorobev.spring.market.core.services.ProductService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @Operation(
            summary = "Запрос на получение отфильтрованного списка продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PageDto.class))
                    )
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<ProductDto> findProducts(
            @RequestParam(defaultValue = "1", name = "page") Integer page,
            @Parameter(description = "Список фильтров типа Map") // С Map не будет работать Swagger, можно заменить на List
            @RequestParam(required = false)  Map<String, String> filterParams
    ) {

        return productService.findAll(filterParams, page - 1);
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

    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto findProduct(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        Product product = productService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Продукт с id = " + id + " не найден"));
        return productConverter.entityToDto(product);
    }

    @Operation(
            summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProduct(@RequestBody (required = false) ProductDto productDto) {
        productService.createNewProduct(productDto);
    }

    @Operation(
            summary = "Запрос на удаление продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно удален", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Long.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        productService.deleteById(id);
    }

    @Operation(
            summary = "Запрос на получение списка категорий продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CategoryDto.class))
                    )
            }
    )
    @GetMapping("/categories")
    public List<CategoryDto> findCategories() {
        return categoryService.findAll().stream()
                .map(categoryConverter::entityToDtoWithoutProducts)
                .collect(Collectors.toList());
    }
}
