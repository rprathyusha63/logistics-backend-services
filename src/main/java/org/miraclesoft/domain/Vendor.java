package org.miraclesoft.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vendor")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vendor {

    @Id
    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @JsonProperty
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", insertable = false, updatable = false)
    private Warehouse warehouse;

    @Column(name = "vendor_business_name")
    private String businessName;

    @Column(name = "vendor_type")
    private String type;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "location")
    private String location;
}
