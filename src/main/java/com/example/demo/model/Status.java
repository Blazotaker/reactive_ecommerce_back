package com.example.demo.model;

public enum Status {
    CREADO("CREADO"), BORRADO("BORRADO");

    private String status;
    private  Status(String status){
        this.status = status;
    }
}
