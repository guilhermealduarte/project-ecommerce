package com.guilhermeduarte.projectecommerce.dto;

import java.util.ArrayList;
import java.util.List;

import com.guilhermeduarte.projectecommerce.entities.Category;
import com.guilhermeduarte.projectecommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	
	private Long id;
	
	@Size(min = 3, max = 100, message = "Deve conter de 3 a 100 caracteres")
	@NotBlank(message = "Campo nome é obrigatório")
	private String name;
	
	@Size(min = 10, message = "O campo descrição deve conter no mínimo 10 caracteres")
	@NotBlank(message = "Campo descrição é obrigatório")
	private String description;
	
	@Positive(message = "O preço deve ser positivo")
	private Double price;
	
	private String imageUrl;
	
	@NotEmpty
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO(Long id, String name, String description, Double price, String imageUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imageUrl = imageUrl;
	}
	
	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imageUrl = entity.getImageUrl();
		
		for(Category cat : entity.getCategories()) {
			categories.add(new CategoryDTO(cat));
		}
	}

	public Long getId() {
		return id;
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

	public List<CategoryDTO> getCategories() {
		return categories;
	}
}
