package com.guilhermeduarte.projectecommerce.controllers;

import java.net.URI;
import java.util.List;

import com.guilhermeduarte.projectecommerce.dto.requests.ProductRequest;
import com.guilhermeduarte.projectecommerce.dto.responses.ProductDetailResponse;
import com.guilhermeduarte.projectecommerce.dto.responses.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.guilhermeduarte.projectecommerce.services.ProductService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping()
	public ResponseEntity<List<ProductResponse>> getAll() {
		List<ProductResponse> productResponse = service.findAll();
		
		return ResponseEntity.ok(productResponse);
	}
	
	@GetMapping(value = "/{uuid}")
	public ResponseEntity<ProductDetailResponse> getByUuid(@PathVariable @NonNull String uuid) {
		ProductDetailResponse productResponse = service.findByUuid(uuid);

		return ResponseEntity.ok(productResponse);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
		ProductResponse productResponse = service.insert(request);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(request.getUuid()).toUri();

		return ResponseEntity.created(uri).body(productResponse);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{uuid}")
	public ResponseEntity<ProductResponse> update(@PathVariable @NonNull String uuid, @Valid @RequestBody ProductRequest request) {
		ProductResponse productResponse = service.update(uuid, request);

		return ResponseEntity.ok(productResponse);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{uuid}")
	public ResponseEntity<Void> destroy(@PathVariable @NonNull String uuid) {
		service.delete(uuid);

		return ResponseEntity.noContent().build();
	}
}
