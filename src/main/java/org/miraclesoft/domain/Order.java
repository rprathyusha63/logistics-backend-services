package org.miraclesoft.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "accessorial_id")
    private String accessorialId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", insertable = false, updatable = false)
    private org.miraclesoft.domain.Warehouse warehouse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id", insertable = false, updatable = false)
    private org.miraclesoft.domain.Vendor vendor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private org.miraclesoft.domain.Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accessorial_id", insertable = false, updatable = false)
    private Accessorials accessorials;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "condition")
    private String condition;

    @Column(name = "total_price")
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "received_on")
    private Timestamp receivedOn;

    @Column(name = "processed_on")
    private Timestamp processedOn;

    @Column(name = "shipped_on")
    private Timestamp shippedOn;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "payment_confirmation_number")
    private String paymentConfirmationNumber;

    public enum OrderStatus {
        PENDING,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELED
    }
}
