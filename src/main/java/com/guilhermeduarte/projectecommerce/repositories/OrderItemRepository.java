package com.guilhermeduarte.projectecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermeduarte.projectecommerce.entities.OrderItem;
import com.guilhermeduarte.projectecommerce.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

}
