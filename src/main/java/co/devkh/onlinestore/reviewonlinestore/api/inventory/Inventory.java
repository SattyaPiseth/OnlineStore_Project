package co.devkh.onlinestore.reviewonlinestore.api.inventory;

import co.devkh.onlinestore.reviewonlinestore.api.product.data.Product;
import co.devkh.onlinestore.reviewonlinestore.api.store.Store;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.Supplier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventory_id;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    private Product product;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal unit_cost;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 100,nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDate last_restock_date;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}