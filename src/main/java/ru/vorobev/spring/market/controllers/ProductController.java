package ru.vorobev.spring.market.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.spring.market.dtos.CreateNewProductDto;
import ru.vorobev.spring.market.dtos.ProductDto;
import ru.vorobev.spring.market.entities.Product;
import ru.vorobev.spring.market.exceptions.ResourceNotFoundException;
import ru.vorobev.spring.market.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> findAllProducts() {
        return productService.findAll();
    }


//    Один из вариантов обработки некорректных запросов (минус - громоздкий)
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
        Product product = productService.findById(id).orElseThrow(()-> new ResourceNotFoundException("Продукт с id = " + id + " не найден"));
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProduct(@RequestBody (required = false) CreateNewProductDto createNewProductDto) {
        productService.createNewProduct(createNewProductDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
