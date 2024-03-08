package com.guilhermeduarte.projectecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermeduarte.projectecommerce.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
