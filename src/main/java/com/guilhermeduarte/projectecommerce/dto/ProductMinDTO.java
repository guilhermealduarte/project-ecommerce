package com.guilhermeduarte.projectecommerce.dto;

import java.util.ArrayList;
import java.util.List;

import com.guilhermeduarte.projectecommerce.entities.Category;
import com.guilhermeduarte.projectecommerce.entities.Product;

public class ProductMinDTO {
	
	private Long id;
	
	private String name;
		
	private Double price;
	
	private String imageUrl;
	
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductMinDTO(Long id, String name, Double price, String imageUrl) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.imageUrl = imageUrl;
	}
	
	public ProductMinDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
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
