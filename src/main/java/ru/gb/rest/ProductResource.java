package ru.gb.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.exception.NotFoundException;
import ru.gb.persist.entity.Product;
import ru.gb.persist.repo.ProductRepository;

import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {

    private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);
    private ProductRepository productRepository;

    @Autowired
    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll() {
        logger.info("Product page update");
        return productRepository.findAll();
    }

    @GetMapping(path = "/{id}/id")
    public Product findById(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Product createProduct(@RequestBody Product product) {
        productRepository.save(product);
        return product;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Product updateProduct(@RequestBody Product product) {
        if (productRepository.findById(product.getId()).isEmpty()) {
            throw new NotFoundException();
        } else {
            productRepository.save(product);
        }
        return product;
    }

    @DeleteMapping(path = ("/{id}/id"))
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }
}
