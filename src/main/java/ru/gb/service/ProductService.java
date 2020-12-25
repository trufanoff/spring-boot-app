package ru.gb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.gb.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll(Specification<Product> spec);

    Optional<Product> findById(Long id);

    void save(Product product);

    void deleteById(Long id);

    Page<Product> findWithFilter(Optional<String> nameFilter,
                                 Optional<BigDecimal> minPrice,
                                 Optional<BigDecimal> maxPrice,
                                 Optional<Integer> page,
                                 Optional<Integer> size,
                                 Optional<String> sort
                                );
}
