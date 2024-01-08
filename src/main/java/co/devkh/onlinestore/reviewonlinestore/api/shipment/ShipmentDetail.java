package co.devkh.onlinestore.reviewonlinestore.api.shipment;

import co.devkh.onlinestore.reviewonlinestore.api.product.data.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shipment_details")
public class ShipmentDetail{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    private Product product;

    private Integer quantity;

    @Column(name = "amount_per_unit",nullable = false,precision = 10,scale = 2)
    private BigDecimal amountPerUnit;

    @Column(precision = 10,scale = 2,nullable = false)
    private BigDecimal amount;
}
