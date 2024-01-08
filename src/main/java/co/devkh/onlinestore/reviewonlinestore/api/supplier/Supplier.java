package co.devkh.onlinestore.reviewonlinestore.api.supplier;

import co.devkh.onlinestore.reviewonlinestore.api.inventory.Inventory;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true,nullable = false)
    private String uuid;
    @Column(nullable = false, unique = true)
    private String company_name;
    @Column(length = 100,nullable = false)
    private String contact_name;
    @Column(length = 150)
    private String contact_title;
    @Column(unique = true)
    private String contact_email;
    private String address;
    @Column(length = 80)
    private String city;
    @Column(length = 50)
    private String state;
    @Column(length = 50)
    private String country;
    @Column(length = 60,nullable = false)
    private String phone;

    @OneToMany(mappedBy = "supplier")
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns =
        @JoinColumn(name = "supplier_id",referencedColumnName = "id"),
            inverseJoinColumns =
                @JoinColumn(name = "cate_id",referencedColumnName = "id"))
    private List<Category> categories;
}
