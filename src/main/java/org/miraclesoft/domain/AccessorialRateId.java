package org.miraclesoft.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AccessorialRateId implements Serializable {
    @Column(name = "accessorial_id")
    private String accessorial_id;

    @Column(name="warehouse_id")
    private String warehouse_id;

    public AccessorialRateId() {
    }

    public AccessorialRateId(String accessorial_id, String warehouse_id) {
        this.accessorial_id = accessorial_id;
        this.warehouse_id = warehouse_id;
    }

    public String getAccessorial_id() {
        return accessorial_id;
    }

    public void setAccessorial_id(String accessorial_id) {
        this.accessorial_id = accessorial_id;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }


}
