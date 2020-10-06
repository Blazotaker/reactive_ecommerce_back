package com.example.demo.configuration;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataBaseConfig {
    @Bean
    public PostgresqlConnectionFactory posgresConfig() {
         return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(5432)  // optional, defaults to 5432
                .username("postgres")
                .password("admin")
                .database("ecommerce")  // optional
                .build());
        //return connectionFactory;
    }
}
