package ru.vorobev.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.core.converters.ProductConverter;
import ru.vorobev.spring.market.core.dao.ProductSpecifications;
import ru.vorobev.spring.market.core.entities.Product;
import ru.vorobev.spring.market.core.repositories.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByFilters(Map<String,String> filterParams) {
        Specification<Product> spec = Specification.where(null);
        if (filterParams.containsKey("filterTitle")) {
            spec = spec.and(ProductSpecifications.withTitle(filterParams.get("filterTitle")));
        }
        if (filterParams.containsKey("filterMin")) {
            spec = spec.and(ProductSpecifications.withMinPrice(Integer.valueOf(filterParams.get("filterMin"))));
        }
        if (filterParams.containsKey("filterMax")) {
            spec = spec.and(ProductSpecifications.withMaxPrice(Integer.valueOf(filterParams.get("filterMax"))));
        }

        return productRepository.findAll(spec);

    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        if (productDto != null) {
            Product product = productConverter.dtoToEntity(productDto);
//            productRepository.save(product);  TODO: перед этим, нужно сначала допилить передачу с фронта категории
        }
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
