package org.miraclesoft.repository;
import org.miraclesoft.domain.VendorProduct;
import org.miraclesoft.domain.VendorProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendorProductRepository extends JpaRepository<VendorProduct, VendorProductId> {
    @Query("SELECT u FROM VendorProduct u WHERE u.vendor.vendorId = :vendor_id")
    List<VendorProduct> findVendorProductsByVendorId(
            @Param("vendor_id") String vendor_id);
}
