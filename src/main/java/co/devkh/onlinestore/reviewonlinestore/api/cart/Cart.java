package co.devkh.onlinestore.reviewonlinestore.api.cart;

import co.devkh.onlinestore.reviewonlinestore.api.product.Product;
import co.devkh.onlinestore.reviewonlinestore.api.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price",nullable = false)
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    private Product product;
}
