package co.devkh.onlinestore.reviewonlinestore.api.payment;

import co.devkh.onlinestore.reviewonlinestore.api.shipment.Shipment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_details")
public class PaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;
}
