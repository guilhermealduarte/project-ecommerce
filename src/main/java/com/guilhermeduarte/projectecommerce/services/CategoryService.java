package com.guilhermeduarte.projectecommerce.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.guilhermeduarte.projectecommerce.dto.requests.CategoryRequest;
import com.guilhermeduarte.projectecommerce.dto.responses.CategoryResponse;
import com.guilhermeduarte.projectecommerce.services.exceptions.DatabaseException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guilhermeduarte.projectecommerce.entities.Category;
import com.guilhermeduarte.projectecommerce.repositories.CategoryRepository;
import com.guilhermeduarte.projectecommerce.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
	public List<CategoryResponse> findAll() {
		List<Category> list = repository.findAll();
		
		return list.stream().map(CategoryResponse::new).toList();
	}

	@Transactional(readOnly = true)
	public CategoryResponse findByUuid(@NonNull String uuid) {
		Category category = repository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

		return new CategoryResponse(category);
	}

	@Transactional
	public CategoryResponse insert(CategoryRequest request) {

		Category category = new Category();

		category.setUuid(UUID.randomUUID());
		category.setName(request.getName());

		Long id = repository.create(category);

        return new CategoryResponse(category);
	}

	@Transactional
	public CategoryResponse update(@NonNull String uuid, CategoryRequest request) {

		Category category = new Category();

		CategoryResponse categoryResponse = null;
		try {
			Optional<Category> response = repository.findByUuid(uuid);

			if(response.isPresent()){
				category.setId(response.get().getId());
				category.setUuid(response.get().getUuid());
				category.setName(request.getName());

				 repository.update(category);

				categoryResponse = new CategoryResponse(category);
			}
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}

		return categoryResponse;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(@NonNull String uuid) {
		if (!repository.existsById(uuid)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}

		try {
			repository.delete(uuid);
		}
	    catch (DataIntegrityViolationException e) {

	    	throw new DatabaseException("Falha de integridade referencial");
	   	}
	}
}
