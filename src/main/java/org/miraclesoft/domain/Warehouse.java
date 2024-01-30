package org.miraclesoft.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "warehouse_name")
    private String warehouseName;

    @Column(name = "location")
    private String location;

    @Column(name = "warehouse_manager")
    private String warehouseManager;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private WarehouseStatus warehouseStatus;

    @Column(name = "operating_hours")
    private String warehouseOperatingHours;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "created_by")
    private String createdBy;

    public enum WarehouseStatus {
        ACTIVE,
        INACTIVE
    }
}
