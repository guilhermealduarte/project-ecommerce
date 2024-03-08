package com.guilhermeduarte.projectecommerce.dto;

import java.time.Instant;

import com.guilhermeduarte.projectecommerce.entities.Payment;

public class PaymentDTO {
	
	private Long orderId;
	
	private Instant moment;

	public PaymentDTO(Long orderId, Instant moment) {
		this.orderId = orderId;
		this.moment = moment;
	}
	
	public PaymentDTO(Payment entity) {
		orderId = entity.getOrder().getId();
		moment = entity.getMoment();
	}

	public Long getOrderId() {
		return orderId;
	}

	public Instant getMoment() {
		return moment;
	}
}
