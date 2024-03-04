package com.guilhermeduarte.projectecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guilhermeduarte.projectecommerce.dto.OrderDTO;
import com.guilhermeduarte.projectecommerce.entities.Order;
import com.guilhermeduarte.projectecommerce.repositories.OrderRepository;
import com.guilhermeduarte.projectecommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Transactional(readOnly = true)
	public Page<OrderDTO> findAll(Pageable pageable) {
		Page<Order> list = repository.findAll(pageable);
		
		return list.map(x -> new OrderDTO(x));
	}
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
				
		return new OrderDTO(entity);
	}
}
