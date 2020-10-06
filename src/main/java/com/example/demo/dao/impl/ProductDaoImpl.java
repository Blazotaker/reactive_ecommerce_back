package com.example.demo.dao.impl;

import com.example.demo.dao.IProductDao;
import com.example.demo.model.Product;
import com.example.demo.model.Status;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Repository
public class ProductDaoImpl implements IProductDao {
    private final PostgresqlConnectionFactory postgresqlConnectionFactory;

    public ProductDaoImpl(PostgresqlConnectionFactory postgresqlConnectionFactory){
        this.postgresqlConnectionFactory = postgresqlConnectionFactory;
    }


    @Override
    public Mono<Product> addProduct(Product product) {
        return postgresqlConnectionFactory.create()
                .flatMapMany(connection -> {
                    product.setProductId(UUID.randomUUID());
                    return connection.createStatement("INSERT INTO products (product_id, name, price, inventory_quantity ,status) VALUES ($1, $2,$3,$4,$5)")
                            .bind("$1", product.getProductId())
                            .bind("$2", product.getName())
                            .bind("$3", product.getPrice())
                            .bind("$4", product.getInventoryQuantity())
                            .bind("$5",product.getStatus().toString())
                            .add()
                            .execute()
                            .flatMap(postgresqlResult -> {
                                 return postgresqlResult.map( (row, rowMetadata) -> {
                                    Status status = row.get("status", Status.class).equals("BORRADO")?Status.BORRADO:Status.CREADO;
                                    return Product.builder().withProductId(product.getProductId())
                                            .withName(row.get("name", String.class))
                                            .withPrice(row.get("proce", Double.class))
                                            .withInventoryQuantity(row.get("inventory_quantity", Integer.class))
                                            .withStatus(status)
                                            .build();
                                }).switchIfEmpty(Mono.just(product));
                            });
                }).single();


    }

    @Override
    public Flux<Product> findAllProducts() {
        return postgresqlConnectionFactory.create()
                .flatMapMany(postgresqlConnection -> {
                    return postgresqlConnection.createStatement("SELECT * FROM products")
                            .execute()
                            .flatMap(postgresqlResult -> {
                                return postgresqlResult.map((row, rowMetadata) -> {
                                    return Product.builder().withProductId(row.get("product_id", UUID.class))
                                            .withName(row.get("name", String.class))
                                            .withPrice(row.get("price", Double.class))
                                            .withInventoryQuantity(row.get("inventory_quantity", Integer.class))
                                            .build();
                                });
                            });
                });
    }

    @Override
    public Mono<Product> findProductById() {
        return null;
    }

    @Override
    public Mono<Product> updateProduct() {
        return null;
    }

    @Override
    public Mono<Void> deleteProductById(UUID productId) {
        System.out.println(productId);
       return postgresqlConnectionFactory.create().flatMapMany(connection -> {
           return connection.createStatement("DELETE FROM products WHERE product_id = $1 ")
                   .bind("$1",productId).execute().flatMap(postgresqlResult -> {
                       System.out.println(postgresqlResult.getRowsUpdated());
                       return postgresqlResult.map((row, rowMetadata) -> {
                           return Product.builder().withProductId(row.get("product_id", UUID.class))
                                   .withName(row.get("name", String.class))
                                   .withPrice(row.get("price", Double.class))
                                   .withInventoryQuantity(row.get("inventory_quantity", Integer.class))
                                   .build();
                       });
                   });
        }).then();
    }
}
