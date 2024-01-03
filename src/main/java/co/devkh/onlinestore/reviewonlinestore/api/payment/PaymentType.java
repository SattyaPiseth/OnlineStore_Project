package co.devkh.onlinestore.reviewonlinestore.api.payment;

import co.devkh.onlinestore.reviewonlinestore.api.order.Order;
import co.devkh.onlinestore.reviewonlinestore.api.shipment.Shipment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "payment_type")
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "type_name",length = 50,nullable = false,unique = true)
    private String typeName;

    @OneToMany(mappedBy = "paymentType")
    private List<Shipment> shipments;

    @OneToMany(mappedBy = "paymentType")
    private List<Order> orders;

    @OneToMany(mappedBy = "paymentType")
    private List<Transaction> transactions;
}
