package org.miraclesoft.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VendorProductId implements Serializable {
    @Column(name = "vendor_id")
    private String vendor_id;

    @Column(name="product_id")
    private String product_id;

    public VendorProductId() {
    }

    public VendorProductId(String vendor_id, String product_id) {
        this.vendor_id = vendor_id;
        this.product_id = product_id;
    }

    // getters and setters...

    public String getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(String vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    // equals() and hashCode()...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendorProductId that = (VendorProductId) o;
        return vendor_id == that.vendor_id && product_id == that.product_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendor_id, product_id);
    }
}
