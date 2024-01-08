package co.devkh.onlinestore.reviewonlinestore.api.order;

import co.devkh.onlinestore.reviewonlinestore.api.payment.PaymentType;
import co.devkh.onlinestore.reviewonlinestore.api.user.data.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true,nullable = false)
    private String uuid;
    @Column(name = "order_date",nullable = false)
    private LocalDateTime date;
    @Column(name = "total_amount",precision = 10,scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status",nullable = false)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderItems;
}
