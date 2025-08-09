package com.guilhermeduarte.projectecommerce.services;

import com.guilhermeduarte.projectecommerce.dto.requests.CategoryRequest;
import com.guilhermeduarte.projectecommerce.dto.requests.ProductRequest;
import com.guilhermeduarte.projectecommerce.dto.responses.ProductDetailResponse;
import com.guilhermeduarte.projectecommerce.dto.responses.ProductResponse;
import com.guilhermeduarte.projectecommerce.entities.Category;
import com.guilhermeduarte.projectecommerce.entities.ProductCategory;
import com.guilhermeduarte.projectecommerce.repositories.CategoryRepository;
import com.guilhermeduarte.projectecommerce.repositories.ProductCategoryRepository;
import com.guilhermeduarte.projectecommerce.services.exceptions.DatabaseException;
import com.guilhermeduarte.projectecommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guilhermeduarte.projectecommerce.entities.Product;
import com.guilhermeduarte.projectecommerce.repositories.ProductRepository;

import java.util.*;

@Service
public class ProductService {

	private final ProductRepository repository;
	private final CategoryRepository categoryRepository;
	private final ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository, ProductCategoryRepository productCategoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional(readOnly = true)
	public List<ProductResponse> findAll() {
		List<Product> list = repository.findAll();
		
		return list.stream().map(ProductResponse::new).toList();
	}

	@Transactional(readOnly = true)
	public ProductDetailResponse findByUuid(@NonNull String uuid) {
		Product product = repository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

		return new ProductDetailResponse(product);
	}

	@Transactional
	public ProductResponse insert(ProductRequest request) {

		Product product = new Product();

		product.setUuid(UUID.randomUUID());
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setImageUrl(request.getImageUrl());

		//Set<Category> categories = new HashSet<>();

		Long idProduct = repository.create(product);

		for(CategoryRequest categoryRequest: request.getCategories()){
			Long idCategory = categoryRepository.findIdByUuid(String.valueOf(categoryRequest.getUuid()));

			productCategoryRepository.create(idProduct, idCategory);

			//Category category = new Category();

			//category.setId(idCategory);
			//category.setUuid(categoryRequest.getUuid());
			//category.setName(categoryRequest.getName());

			//categories.add(category);
		}

		//product.setCategories(categories);

		return new ProductResponse(product);
	}

	@Transactional
	public ProductResponse update(@NonNull String uuid, ProductRequest request) {
		Product product = new Product();

		ProductResponse productResponse = null;

		try {
			Optional<Product> response = repository.findByUuid(uuid);

			if(response.isPresent()){
				product.setId(response.get().getId());
				product.setUuid(response.get().getUuid());
				product.setName(request.getName());
				product.setDescription(request.getDescription());
				product.setPrice(request.getPrice());
				product.setImageUrl(request.getImageUrl());

				repository.update(product);

				productResponse = new ProductResponse(product);
			}
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}

		return productResponse;
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
//
//	private void copyDTOToEntity(ProductDTO dto, Product entity) {
//		entity.setName(dto.getName());
//		entity.setDescription(dto.getDescription());
//		entity.setPrice(dto.getPrice());
//		entity.setImageUrl(dto.getImageUrl());
//
//		entity.getCategories().clear();
////		for(CategoryDTO categoryDTO : dto.getCategories()) {
////			Category category = categoryRepository.findById(categoryDTO.getId());
////			category.setId(categoryDTO.getId());
////			entity.getCategories().add(category);
////		}
//	}
}
