package org.miraclesoft.service;

import org.miraclesoft.domain.ProductCategory;
import org.miraclesoft.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

	private final ProductCategoryRepository productCategoryRepository;

	@Autowired
	public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}

	public List<ProductCategory> getAllCategories() {
		return productCategoryRepository.findAll();
	}
	public ProductCategory getCategoryById(String categoryId) {
		return productCategoryRepository.findById(categoryId).get();
	}

	public ProductCategory saveCategory(ProductCategory category) {
		return productCategoryRepository.save(category);
	}

	public ProductCategory updateCategory(ProductCategory category) {
		return productCategoryRepository.save(category);

	}

	public String deleteCategory(String categoryId) {
		if (productCategoryRepository.existsById(categoryId)) {
			productCategoryRepository.deleteById(categoryId);
			return categoryId;
		} else {
			return "";
		}
	}
	}
