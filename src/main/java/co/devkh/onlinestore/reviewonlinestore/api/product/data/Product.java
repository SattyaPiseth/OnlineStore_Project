package co.devkh.onlinestore.reviewonlinestore.api.product.data;

import co.devkh.onlinestore.reviewonlinestore.api.brand.Brand;
import co.devkh.onlinestore.reviewonlinestore.api.cart.Cart;
import co.devkh.onlinestore.reviewonlinestore.api.inventory.Inventory;
import co.devkh.onlinestore.reviewonlinestore.api.order.OrderItems;
import co.devkh.onlinestore.reviewonlinestore.api.shipment.ShipmentDetail;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.Supplier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String uuid;
    @Column(name = "pro_code", length = 30, nullable = false, unique = true)
    private String code;
    private String name;
//    private String slug;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "image_url")
    private String image;
    @Column(name = "unit_price")
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "cate_id")
    private Category category;


    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "product")
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts;

    @OneToMany(mappedBy = "product")
    private List<OrderItems> orderItems;

    @OneToMany(mappedBy = "product")
    private List<ShipmentDetail> shipmentDetails;
}
