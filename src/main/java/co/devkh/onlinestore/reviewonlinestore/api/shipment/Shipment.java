package co.devkh.onlinestore.reviewonlinestore.api.shipment;

import co.devkh.onlinestore.reviewonlinestore.api.payment.PaymentDetail;
import co.devkh.onlinestore.reviewonlinestore.api.payment.PaymentType;
import co.devkh.onlinestore.reviewonlinestore.api.store.Store;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "shipment_uuid",nullable = false,unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false,name = "time_created")
    private LocalDateTime timeCreated;

    @ManyToOne
    @JoinColumn(name = "shipment_type_id")
    private ShipmentType shipmentType;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @Column(columnDefinition = "TEXT",name = "shipping_address")
    private String shippingAddress;

    @Column(columnDefinition = "TEXT",name = "billing_address")
    private String billingAddress;

    @Column(precision = 10,scale = 2,nullable = false,name = "products_price")
    private BigDecimal productsPrice;

    @Column(precision = 10,scale = 2,nullable = false,name = "delivery_cost")
    private BigDecimal deliveryCost;

    private Double discount;

    @Column(precision = 10,scale = 2,nullable = false,name = "final_amount")
    private BigDecimal finalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shipment")
    private List<PaymentDetail> paymentDetails;

    @OneToMany(mappedBy = "shipment")
    private List<ShipmentDetail> shipmentDetails;

    @OneToMany(mappedBy = "shipment")
    private List<ShipmentStatus> shipmentStatuses;
}
