package com.stackroute.elasticsearch.controller;

import com.stackroute.elasticsearch.model.Product;
import com.stackroute.elasticsearch.dao.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //getting a product with id
    @GetMapping("/{id}")
    public Map<String, Object> getProductById(@PathVariable UUID id){
        return productRepository.getProductById(id);
    }


    //inserting product
    @PostMapping
    public Product insertBook(@RequestBody Product product) throws Exception {
        return productRepository.insertProduct(product);
    }
    //update a product
    @PutMapping("/{id}")
    public Map<String, Object> updateProductById(@RequestBody Product product, @PathVariable UUID id) {
        return productRepository.updateProductById(id, product);
    }

    //deletion of a product
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable UUID id) {
        productRepository.deleteProductById(id);
    }

}
