package com.guilhermeduarte.projectecommerce.controllers;

import java.net.URI;
import java.util.List;

import com.guilhermeduarte.projectecommerce.dto.requests.CategoryRequest;
import com.guilhermeduarte.projectecommerce.dto.responses.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.guilhermeduarte.projectecommerce.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

	private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping()
	public ResponseEntity<List<CategoryResponse>> getAll() {
		List<CategoryResponse> categoryResponse = service.findAll();
		
		return ResponseEntity.ok(categoryResponse);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/{uuid}")
	public ResponseEntity<CategoryResponse> getByUuid(@PathVariable @NonNull String uuid) {
		CategoryResponse categoryResponse = service.findByUuid(uuid);

		return ResponseEntity.ok(categoryResponse);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {

		CategoryResponse categoryResponse = service.insert(request);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(request.getUuid()).toUri();

		return ResponseEntity.created(uri).body(categoryResponse);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{uuid}")
	public ResponseEntity<CategoryResponse> update(@PathVariable @NonNull String uuid, @Valid @RequestBody CategoryRequest request) {
		CategoryResponse categoryResponse = service.update(uuid, request);

		return ResponseEntity.ok(categoryResponse);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{uuid}")
	public ResponseEntity<Void> destroy(@PathVariable @NonNull String uuid) {
		service.delete(uuid);

		return ResponseEntity.noContent().build();
	}
}
