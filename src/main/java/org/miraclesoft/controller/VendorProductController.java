package org.miraclesoft.controller;
import org.miraclesoft.domain.VendorProduct;
import org.miraclesoft.domain.VendorProductId;
import org.miraclesoft.service.VendorProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/vendorproducts")
public class VendorProductController {
	private final VendorProductService vendorProductService;


	@Autowired
	public VendorProductController(VendorProductService vendorProductService) {
		this.vendorProductService = vendorProductService;
	}

	@GetMapping
	public Flux<ResponseEntity<VendorProduct>> getAllVendorProducts() {

		return Flux.fromIterable(vendorProductService.getAllVendorProducts())
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	@GetMapping("/{vendor_id}")
	public Flux<ResponseEntity<VendorProduct>> getVendorProductsByVendorId(@PathVariable String vendor_id) {

		return Flux.fromIterable(vendorProductService.getAllVendorProductsByVendorId(vendor_id))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping()
	public Mono<ResponseEntity<VendorProduct>> createVendorProductInventory(@RequestBody VendorProduct vendorProduct) {
		VendorProduct savedVendorProduct =  vendorProductService.saveVendorProduct(vendorProduct);
		return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(savedVendorProduct));
	}

	@PutMapping()
	public Mono<ResponseEntity<?>> updateVendorProductInventory(@RequestBody VendorProduct vendorProduct) {
		VendorProduct existing = vendorProductService.getById(vendorProduct.getId());
		if (existing != null) {
			existing.setProduct_warehouse_plot(vendorProduct.getProduct_warehouse_plot());
			existing.setQuantity(vendorProduct.getQuantity());
			VendorProduct updatedVendorProduct = vendorProductService.saveVendorProduct(existing);

			return Mono.just(ResponseEntity.ok(updatedVendorProduct));
		} else {
			return Mono
					.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor Product not found with Vendor ID: " + vendorProduct.getId().getVendor_id()
					+" Product Id "+vendorProduct.getId().getProduct_id()));
		}
	}

	@DeleteMapping("{vendorId}/{productId}")
	public Mono<ResponseEntity<?>> deleteVendorProductInventory(@PathVariable String vendorId,
																			@PathVariable String productId) {
		VendorProductId deletedId =  vendorProductService.deleteVendorProduct(new VendorProductId(vendorId, productId));

		if (deletedId != null) {
			return Mono.just(ResponseEntity.status(HttpStatus.OK)
					.body("Inventory for Vendor ID: " + vendorId + " and Product ID: "+ productId+ " has been deleted."));
		} else {
			return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Inventory for Vendor ID: " + vendorId + " and Product ID: "+ productId+ " was not found."));
		}

	}

}