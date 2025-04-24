package com.example.restapi.model;

import jakarta.validation.constraints.NotBlank;

public class User {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    // Add Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
