package ru.vorobev.spring.market.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.dtos.CreateNewProductDto;
import ru.vorobev.spring.market.dtos.ProductDto;
import ru.vorobev.spring.market.entities.Product;
import ru.vorobev.spring.market.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(pr -> new ProductDto(pr.getId(), pr.getTitle(), pr.getPrice())).collect(Collectors.toList());
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void createNewProduct(CreateNewProductDto createNewProductDto) {
        if (createNewProductDto != null) {
            Product product = new Product();
            product.setTitle(createNewProductDto.getTitle());
            product.setPrice(createNewProductDto.getPrice());
            productRepository.save(product);
        }
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
