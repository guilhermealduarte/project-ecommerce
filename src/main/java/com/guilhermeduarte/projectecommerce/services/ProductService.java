package com.guilhermeduarte.projectecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guilhermeduarte.projectecommerce.dto.CategoryDTO;
import com.guilhermeduarte.projectecommerce.dto.ProductDTO;
import com.guilhermeduarte.projectecommerce.dto.ProductMinDTO;
import com.guilhermeduarte.projectecommerce.entities.Category;
import com.guilhermeduarte.projectecommerce.entities.Product;
import com.guilhermeduarte.projectecommerce.repositories.CategoryRepository;
import com.guilhermeduarte.projectecommerce.repositories.ProductRepository;
import com.guilhermeduarte.projectecommerce.services.exceptions.DatabaseException;
import com.guilhermeduarte.projectecommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
		Page<Product> list = repository.searchByName(name, pageable);
		
		return list.map(x -> new ProductMinDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {	
		Product entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		
		ProductDTO dto = new ProductDTO(entity);
		
		return dto;
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {	
		
		Product entity = new Product();
		
		copyDTOToEntity(dto, entity);
				
		entity = repository.save(entity);
		
		ProductDTO newDTO = new ProductDTO(entity);
		
		return newDTO;
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {	
		
		try {
			Product entity = repository.getReferenceById(id);
			
			copyDTOToEntity(dto, entity);
						
			entity = repository.save(entity);
			
			ProductDTO newDTO = new ProductDTO(entity);
			
			return newDTO;
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {	
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		
		try {
			repository.deleteById(id);    		
		}
	    catch (DataIntegrityViolationException e) {
	        
	    	throw new DatabaseException("Falha de integridade referencial");
	   	}
	}

	private void copyDTOToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImageUrl(dto.getImageUrl());
		
		entity.getCategories().clear();
		for(CategoryDTO categoryDTO : dto.getCategories()) {
			Category category = categoryRepository.getReferenceById(categoryDTO.getId());
			category.setId(categoryDTO.getId());
			entity.getCategories().add(category);
		}
	}
}
