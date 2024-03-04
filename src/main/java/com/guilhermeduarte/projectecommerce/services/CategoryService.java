package com.guilhermeduarte.projectecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guilhermeduarte.projectecommerce.dto.CategoryDTO;
import com.guilhermeduarte.projectecommerce.entities.Category;
import com.guilhermeduarte.projectecommerce.repositories.CategoryRepository;
import com.guilhermeduarte.projectecommerce.services.exceptions.DatabaseException;
import com.guilhermeduarte.projectecommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(@NonNull Pageable pageable) {
		Page<Category> list = repository.findAll(pageable);
		
		return list.map(x -> new CategoryDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(@NonNull Long id) {
		Category entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		
		CategoryDTO dto = new CategoryDTO(entity);
		
		return dto;
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {	
		
		Category entity = new Category();
		
		copyDTOToEntity(dto, entity);
		
		entity = repository.save(entity);
		
		CategoryDTO newDTO = new CategoryDTO(entity);
		
		return newDTO;
	}
	
	@Transactional
	public CategoryDTO update(@NonNull Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getReferenceById(id);
			
			copyDTOToEntity(dto, entity);
			
			entity = repository.save(entity);
			
			CategoryDTO newDTO = new CategoryDTO(entity);
			
			return newDTO;
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(@NonNull Long id) {
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
	
	private void copyDTOToEntity(CategoryDTO dto, Category entity) {
		entity.setName(dto.getName());
	}
}
