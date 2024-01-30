package org.miraclesoft.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "warehouse_accessorial_rates")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessorialRates {

    @EmbeddedId
    private AccessorialRateId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accessorial_id", referencedColumnName = "accessorial_id", insertable = false, updatable = false)
    private Accessorials accessorials;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id", insertable = false, updatable = false)
    private Warehouse warehouse;

    @Column(name = "accessorial_rate")
    private double accessorialRate;
}
