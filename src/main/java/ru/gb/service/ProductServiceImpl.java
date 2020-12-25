package ru.gb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.persist.entity.Product;
import ru.gb.persist.repo.ProductRepository;
import ru.gb.persist.repo.ProductSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll(Specification<Product> spec) {
        return productRepository.findAll(spec);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findWithFilter(Optional<String> nameFilter, Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice,
                                        Optional<Integer> page, Optional<Integer> size, Optional<String> sort) {
        Specification<Product> spec = Specification.where(null);

        if (nameFilter.isPresent() && !nameFilter.get().isEmpty()) {
            logger.info("Adding {} to filter", nameFilter.get());
            spec = spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }
        if (minPrice.isPresent()) {
            logger.info("Adding {} to filter", minPrice.get());
            spec = spec.and(ProductSpecification.minPriceFilter(minPrice.get()));
        }
        if (maxPrice.isPresent()) {
            logger.info("Adding {} to filter", maxPrice.get());
            spec = spec.and(ProductSpecification.maxPriceFilter(maxPrice.get()));
        }

        return productRepository.findAll(spec, PageRequest.of(page.orElse(1)-1, size.orElse(4), Sort.by(Sort.Direction.ASC, sort.orElse("id"))));
    }
}
