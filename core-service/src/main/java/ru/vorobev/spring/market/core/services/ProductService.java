package ru.vorobev.spring.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.vorobev.spring.market.api.PageDto;
import ru.vorobev.spring.market.api.ProductDto;
import ru.vorobev.spring.market.core.converters.ProductConverter;
import ru.vorobev.spring.market.core.repositories.specifications.ProductSpecifications;
import ru.vorobev.spring.market.core.entities.Product;
import ru.vorobev.spring.market.core.repositories.ProductRepository;
import ru.vorobev.spring.market.core.validators.ProductValidator;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private  final ProductValidator productValidator;
    @Value("${pagination.page.size.default}")
    private Integer defaultPageSize;

    public PageDto<ProductDto> findAll(Map<String,String> filterParams, int page) {

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

        if (page < 0) {
            page = 1;
        }

        Page<ProductDto> jpaPage = productRepository.findAll(spec, PageRequest.of(page, defaultPageSize))
                .map(productConverter::entityToDto);

        PageDto<ProductDto> out = new PageDto<>();
        out.setPage(jpaPage.getNumber());
        out.setItems(jpaPage.getContent());
        out.setTotalPages(jpaPage.getTotalPages());

        return out;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        if (productDto != null) {
            productValidator.validate(productDto);
            Product product = productConverter.dtoToEntity(productDto);
            productRepository.save(product);
        }
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
