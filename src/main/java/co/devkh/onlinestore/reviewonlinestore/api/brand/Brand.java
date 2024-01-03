package co.devkh.onlinestore.reviewonlinestore.api.brand;

import co.devkh.onlinestore.reviewonlinestore.api.product.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String brand_uuid;
    @Column(length = 100,nullable = false)
    private String brand_name;

    @ManyToMany(mappedBy = "brands")
//    @JoinTable(joinColumns =
//    @JoinColumn(name = "brand_id",referencedColumnName = "id"),
//            inverseJoinColumns =
//            @JoinColumn(name = "cate_id",referencedColumnName = "id"))
    private List<Category> categories;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
