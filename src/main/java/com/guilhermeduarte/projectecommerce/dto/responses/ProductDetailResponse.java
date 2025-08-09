package com.guilhermeduarte.projectecommerce.dto.responses;

import com.guilhermeduarte.projectecommerce.entities.Product;

import java.util.UUID;

public class ProductDetailResponse {

    private UUID uuid;

    private String name;

    private String description;

    private Double price;

    private String imageUrl;

    public ProductDetailResponse(Product product) {
        this.uuid = product.getUuid();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
