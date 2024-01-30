package org.miraclesoft.service;

import org.miraclesoft.domain.Product;
import org.miraclesoft.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductbyID(String productID) {
		return productRepository.findById(productID).get();
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(Product product) {
		productRepository.save(product);
		return product;
	}

	public String deleteProduct(String productID) {
		if (productRepository.existsById(productID)) {
			productRepository.deleteById(productID);
			return productID;
		} else {
			return null;
		}
	}
}
