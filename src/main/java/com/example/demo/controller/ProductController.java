package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.IProductService;
import com.example.demo.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public Flux<Product> findAllProducts(){
        return productService.findAllProducts();
    }

    @PostMapping
    public Mono<Product> addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @DeleteMapping("{productId}")
    public Mono<Void> deleteProduct(@PathVariable UUID productId){
        System.out.println(productId);
        return productService.deleteProductById(productId);
    }


}
