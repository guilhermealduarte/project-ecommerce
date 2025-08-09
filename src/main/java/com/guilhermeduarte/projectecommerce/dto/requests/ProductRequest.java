package com.guilhermeduarte.projectecommerce.dto.requests;

import com.guilhermeduarte.projectecommerce.entities.Category;
import com.guilhermeduarte.projectecommerce.entities.Product;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductRequest {

    private UUID uuid;

    @Size(min = 3, max = 100, message = "Deve conter de 3 a 100 caracteres")
    @NotBlank(message = "Campo nome é obrigatório")
    private String name;

    @Size(min = 10, message = "O campo descrição deve conter no mínimo 10 caracteres")
    @NotBlank(message = "Campo descrição é obrigatório")
    private String description;

    @NotNull(message = "O campo preço é requerido")
    @Positive(message = "O preço deve ser positivo")
    private Double price;

    private String imageUrl;

    @NotEmpty(message = "Deve ter ao menos uma categoria")
    private List<CategoryRequest> categories = new ArrayList<>();

    public ProductRequest() {}

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

    public List<CategoryRequest> getCategories() {
        return categories;
    }
}
