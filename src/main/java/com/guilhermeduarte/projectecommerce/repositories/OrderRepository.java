package com.guilhermeduarte.projectecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermeduarte.projectecommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
