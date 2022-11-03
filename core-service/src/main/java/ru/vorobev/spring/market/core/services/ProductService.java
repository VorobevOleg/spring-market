package ru.vorobev.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.core.converters.ProductConverter;
import ru.vorobev.spring.market.core.entities.Product;
import ru.vorobev.spring.market.core.repositories.ProductRepository;

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
