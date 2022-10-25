package ru.vorobev.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.converters.ProductConverter;
import ru.vorobev.spring.market.dtos.ProductDto;
import ru.vorobev.spring.market.entities.Category;
import ru.vorobev.spring.market.entities.Product;
import ru.vorobev.spring.market.exceptions.ResourceNotFoundException;
import ru.vorobev.spring.market.repositories.CategoryRepository;
import ru.vorobev.spring.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        if (productDto != null) {
            Product product = productConverter.dtoToEntity(productDto);
            productRepository.save(product);
        }
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
