package ru.vorobev.spring.market.core.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.vorobev.spring.market.core.entities.Product;

public class ProductSpecifications {

    public static Specification<Product> withTitle(String title) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Product> withMinPrice(Integer minPrice) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> withMaxPrice(Integer maxPrice) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
