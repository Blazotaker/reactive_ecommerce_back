package com.example.demo.service.impl;

import com.example.demo.dao.IProductDao;
import com.example.demo.dao.impl.ProductDaoImpl;
import com.example.demo.model.Product;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ProductServiceImpl implements IProductService {
    private final IProductDao productDao;

    @Autowired
    public ProductServiceImpl(IProductDao productDao){
        this.productDao = productDao;
    }

    @Override
    public Flux<Product> findAllProducts() {
        return productDao.findAllProducts().log();
    }

    @Override
    public Mono<Product> addProduct(Product product) {
        return productDao.addProduct(product).log();
    }

    @Override
    public Mono<Void> deleteProductById(UUID productId) {
        return productDao.deleteProductById(productId).log();
    }
}
