package com.guilhermeduarte.projectecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermeduarte.projectecommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
