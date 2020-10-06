package com.example.demo.dao;

import com.example.demo.model.Product;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface IProductDao {

    public Mono<Product> addProduct(Product product);
    public Flux<Product> findAllProducts();
    public Mono<Product> findProductById();
    public Mono<Product> updateProduct();
    public Mono<Void>    deleteProductById(UUID productId);
}
