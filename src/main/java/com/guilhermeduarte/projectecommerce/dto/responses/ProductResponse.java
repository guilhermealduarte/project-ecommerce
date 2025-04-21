package com.guilhermeduarte.projectecommerce.dto.responses;

import com.guilhermeduarte.projectecommerce.entities.Product;

import java.util.UUID;

public class ProductResponse {

    private UUID uuid;

    private String name;

    private Double price;

    private String imageUrl;

    public ProductResponse(Product product) {
        this.uuid = product.getUuid();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
