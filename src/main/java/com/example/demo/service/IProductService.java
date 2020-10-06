package com.example.demo.service;

import com.example.demo.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface IProductService {

    public Flux<Product> findAllProducts();
    public Mono<Product> addProduct(Product product);
    public Mono<Void> deleteProductById(UUID productId);
}
