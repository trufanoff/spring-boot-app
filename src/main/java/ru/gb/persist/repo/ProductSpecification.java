package ru.gb.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.gb.persist.entity.Product;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> nameLike(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> minPriceFilter(BigDecimal minPrice){
        return (root, query, builder) -> builder.ge(root.get("price"), minPrice);
    }

    public static Specification<Product> maxPriceFilter(BigDecimal maxPrice){
        return (root, query, builder) -> builder.le(root.get("price"), maxPrice);
    }

}
