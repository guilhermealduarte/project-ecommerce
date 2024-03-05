package com.guilhermeduarte.projectecommerce.dto;

import com.guilhermeduarte.projectecommerce.entities.OrderItem;

public class OrderItemDTO {
	
	private Long productId;
	
	private String name;
	
	private Integer quantity;
	
	private Double price;
	
	private String imageUrl;

	public OrderItemDTO(Long productId, String name, Integer quantity, Double price, String imageUrl) {
		this.productId = productId;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.imageUrl = imageUrl;
	}
	
	public OrderItemDTO(OrderItem entity) {
		productId = entity.getProduct().getId();
		name = entity.getProduct().getName();
		quantity = entity.getQuantity();
		price = entity.getPrice();
		imageUrl = entity.getProduct().getImageUrl();
	}
	
	public Long getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Double getPrice() {
		return price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Double getSubTotal() {
		return quantity * price;
	}
}
