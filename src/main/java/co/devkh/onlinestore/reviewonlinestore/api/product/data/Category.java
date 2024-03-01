package co.devkh.onlinestore.reviewonlinestore.api.product.data;

import co.devkh.onlinestore.reviewonlinestore.api.brand.data.Brand;
import co.devkh.onlinestore.reviewonlinestore.api.supplier.data.Supplier;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @ManyToMany(mappedBy = "categories")
    private List<Supplier> suppliers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns =
        @JoinColumn(name = "cate_id",referencedColumnName = "id"),
            inverseJoinColumns =
                @JoinColumn(name = "brand_id",referencedColumnName = "id"))
    private List<Brand> brands;
}
