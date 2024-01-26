package co.devkh.onlinestore.reviewonlinestore.api.brand.data;

import co.devkh.onlinestore.reviewonlinestore.api.product.data.Category;
import co.devkh.onlinestore.reviewonlinestore.api.product.data.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String brandUuid;
    @Column(length = 100,nullable = false)
    private String brandName;


    @ManyToMany(mappedBy = "brands")
//    @JoinTable(joinColumns =
//    @JoinColumn(name = "brand_id",referencedColumnName = "id"),
//            inverseJoinColumns =
//            @JoinColumn(name = "cate_id",referencedColumnName = "id"))
    private Set<Category> categories;

    @OneToMany(mappedBy = "brand")
    private List<Product> products;
}
