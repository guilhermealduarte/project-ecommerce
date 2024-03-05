package com.guilhermeduarte.projectecommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guilhermeduarte.projectecommerce.dto.OrderDTO;
import com.guilhermeduarte.projectecommerce.dto.OrderItemDTO;
import com.guilhermeduarte.projectecommerce.entities.Order;
import com.guilhermeduarte.projectecommerce.entities.OrderItem;
import com.guilhermeduarte.projectecommerce.entities.OrderStatus;
import com.guilhermeduarte.projectecommerce.entities.Product;
import com.guilhermeduarte.projectecommerce.entities.User;
import com.guilhermeduarte.projectecommerce.repositories.OrderItemRepository;
import com.guilhermeduarte.projectecommerce.repositories.OrderRepository;
import com.guilhermeduarte.projectecommerce.repositories.ProductRepository;
import com.guilhermeduarte.projectecommerce.services.exceptions.ResourceNotFoundException;

import jakarta.validation.Valid;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional(readOnly = true)
	public Page<OrderDTO> findAll(@NonNull Pageable pageable) {
		Page<Order> list = repository.findAll(pageable);
		
		return list.map(x -> new OrderDTO(x));
	}
	
	@Transactional(readOnly = true)
	public OrderDTO findById(@NonNull Long id) {
		Order entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
				
		return new OrderDTO(entity);
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order();
		
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setUser(user);
		
		for(OrderItemDTO itemDTO : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDTO.getProductId());
			
			OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}
}
