package com.guilhermeduarte.projectecommerce.dto;

import com.guilhermeduarte.projectecommerce.entities.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDTO {

	private Long id;
	
	@Size(min = 3, max = 100, message = "Deve conter de 3 a 100 caracteres")
	@NotBlank(message = "Campo nome é obrigatório")
	private String name;

	public CategoryDTO(Long id,String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category entity) {
		id = entity.getId();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
