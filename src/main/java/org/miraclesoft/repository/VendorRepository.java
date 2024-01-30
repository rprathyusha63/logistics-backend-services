package org.miraclesoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.miraclesoft.domain.Vendor;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, String> {
    @Query("SELECT u FROM Vendor u WHERE u.warehouse.warehouseId = :warehouse_id")
    public List<Vendor> findVendorsByWarehouseId(@Param("warehouse_id") String warehouse_id);


    //@Query("SELECT v FROM Vendor v join Warehouse w on v.warehouseId = w.warehouseId WHERE v.vendorId = :vendor_id")
    //public Vendor findByVendorId(@Param("vendor_id") String vendor_id);

}