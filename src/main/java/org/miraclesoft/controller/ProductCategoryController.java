package org.miraclesoft.controller;

import org.miraclesoft.domain.ProductCategory;
import org.miraclesoft.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/product-categories")
public class ProductCategoryController {

	private final ProductCategoryService productCategoryService;

	@Autowired
	public ProductCategoryController(ProductCategoryService productCategoryService) {
		this.productCategoryService=productCategoryService;
	}

	@GetMapping()
	public Flux<ResponseEntity<ProductCategory>> getAllCategories() {

		return Flux.fromIterable(productCategoryService.getAllCategories())
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping("/{categoryId}")
	public Mono<ResponseEntity<?>> getProductCategoryById(@PathVariable String categoryId) {
		ProductCategory productCategory = productCategoryService.getCategoryById(categoryId);
		if (productCategory != null) {
			return Mono.just(ResponseEntity.status(HttpStatus.OK).body(productCategory));
		} else {
			return Mono
					.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with ID " + categoryId));
		}
	}


	@PostMapping
	public Mono<ResponseEntity<ProductCategory>> createCategory(@RequestBody ProductCategory category) {
		category.setCategoryId(UUID.randomUUID().toString());
		ProductCategory savedCategory = productCategoryService.saveCategory(category);
		return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(savedCategory));
	}

	@PutMapping("/{categoryId}")
	public Mono<ResponseEntity<?>> updateCategory(@PathVariable String categoryId, @RequestBody ProductCategory category) {
		ProductCategory existingCategory= productCategoryService.getCategoryById(categoryId);
		if (existingCategory != null) {
			existingCategory.setCategoryName(category.getCategoryName());
			ProductCategory updatedCategory = productCategoryService.saveCategory(existingCategory);

			return Mono.just(ResponseEntity.ok(updatedCategory));
		} else {
			return Mono
					.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with ID: " + categoryId));
		}
	}


	@DeleteMapping("/{categoryId}")
	public Mono<ResponseEntity<String>> deleteCategory(@PathVariable String categoryId) {
		String deletedId = productCategoryService.deleteCategory(categoryId);

		if (deletedId != null) {
			return Mono.just(ResponseEntity.status(HttpStatus.OK)
					.body("category with ID: " + deletedId + " has been deleted."));
		} else {
			return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("category not found with ID: " + categoryId));
		}
	}
}