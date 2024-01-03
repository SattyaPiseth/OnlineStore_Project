package co.devkh.onlinestore.reviewonlinestore.api.payment;

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
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer id;
    @Column(name = "transaction_uuid",nullable = false,unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @Column(nullable = false,name = "transaction_date")
    private LocalDateTime transactionDate;
    @Column(precision = 10 , scale = 2,name = "total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    private ResponseMessage responseMessage;

    @OneToMany(mappedBy = "transaction")
    private List<PaymentDetail> paymentDetails;
}
