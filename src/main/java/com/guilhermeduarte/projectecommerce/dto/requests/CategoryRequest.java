package com.guilhermeduarte.projectecommerce.dto.requests;

import com.guilhermeduarte.projectecommerce.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CategoryRequest {

    private UUID uuid;

    @Size(min = 3, max = 100, message = "Deve conter de 3 a 100 caracteres")
    @NotBlank(message = "Campo nome é obrigatório")
    private String name;

    public CategoryRequest() {}

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
