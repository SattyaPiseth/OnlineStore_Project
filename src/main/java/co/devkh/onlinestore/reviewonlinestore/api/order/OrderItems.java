package co.devkh.onlinestore.reviewonlinestore.api.order;

import co.devkh.onlinestore.reviewonlinestore.api.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer id;
    @Column(unique = true,nullable = false)
    private String uuid;
    @Column(name = "sub_quantity",nullable = false)
    private Integer quantity;
    @Column(name = "sub_amount",nullable = false,precision = 10,scale = 2)
    private BigDecimal amount;

    private Double discount;

    @ManyToOne
    @JoinColumn(name = "pro_id",unique = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id",unique = true)
    private Order order;
}
