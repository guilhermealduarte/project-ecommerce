package com.guilhermeduarte.projectecommerce.dto.responses;

import com.guilhermeduarte.projectecommerce.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CategoryResponse {

    private UUID uuid;

    private String name;

    public CategoryResponse(Category category) {
        uuid = category.getUuid();
        name = category.getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
