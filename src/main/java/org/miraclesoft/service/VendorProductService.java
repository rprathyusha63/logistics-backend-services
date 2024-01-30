package org.miraclesoft.service;

import org.miraclesoft.domain.VendorProduct;
import org.miraclesoft.domain.VendorProductId;
import org.miraclesoft.repository.VendorProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorProductService {

	private final VendorProductRepository vendorProductRepository;

	@Autowired
	public VendorProductService(VendorProductRepository vendorProductRepository) {
		this.vendorProductRepository = vendorProductRepository;
	}

	public List<VendorProduct> getAllVendorProducts() {
		return vendorProductRepository.findAll();
	}
	public List<VendorProduct> getAllVendorProductsByVendorId(String vendorid) {
		return vendorProductRepository.findVendorProductsByVendorId(vendorid);
	}

	public VendorProduct saveVendorProduct(VendorProduct vendorProduct){
		return vendorProductRepository.save(vendorProduct);
	}

	public VendorProduct getById(VendorProductId id){
		return vendorProductRepository.findById(id).orElse(null);
	}

	public VendorProductId deleteVendorProduct(VendorProductId vendorProductId) {
		if (vendorProductRepository.existsById(vendorProductId)) {
			vendorProductRepository.deleteById(vendorProductId);
			return vendorProductId;
		} else {
			return null;
		}
	}
}
