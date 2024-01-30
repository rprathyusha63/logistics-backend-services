package org.miraclesoft.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vendor_product_inventory")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorProduct {

	@EmbeddedId
	private org.miraclesoft.domain.VendorProductId id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id", insertable = false, updatable = false)
	private Vendor vendor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
	private Product product;

	@Column(name = "quantity")
	private long quantity;

	@Column(name = "product_warehouse_plot")
	private String product_warehouse_plot;
	}
