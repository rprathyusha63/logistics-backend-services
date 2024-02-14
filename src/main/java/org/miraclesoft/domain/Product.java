package org.miraclesoft.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

	@Id
	@Column(name = "product_id")
	private String productID;

	@Column(name = "product_model")
	private String productModel;

	@Column(name="category_name")
	private String categoryName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_name", referencedColumnName = "category_name", insertable = false, updatable = false)
	private org.miraclesoft.domain.ProductCategory category;

	@Column(name = "brand")
	private String brand;

	@Column(name = "size")
	private String size;

	@Column(name = "weight")
	private String weight;

	@Column(name = "dimensions")
	private String dimensions;

	@Column(name = "sku_or_item_number")
	private String skuNumber;

	@Column(name = "material")
	private String material;

	@Column(name = "no_of_pieces")
	private String noOfPieces;

	@Column(name = "resolution")
	private String resolution;
}
