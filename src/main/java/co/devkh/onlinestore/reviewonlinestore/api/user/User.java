package co.devkh.onlinestore.reviewonlinestore.api.user;

import co.devkh.onlinestore.reviewonlinestore.api.address.Address;
import co.devkh.onlinestore.reviewonlinestore.api.cart.Cart;
import co.devkh.onlinestore.reviewonlinestore.api.order.Order;
import co.devkh.onlinestore.reviewonlinestore.api.shipment.Shipment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String uuid;
    @Column(unique = true, length = 80, nullable = false)
    private String username;
    @Column(unique = true, length = 80, nullable = false)
    private String email;
    @Column(name = "encrypted_passwd",nullable = false)
    private String password;
    @Column(length = 30)
    private String nickName;
    @Column(length = 10)
    private String verifiedCode;
    private Boolean isVerified;
    private Boolean isDeleted;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns =
        @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns =
                    @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @OneToMany(mappedBy = "user")
    private List<Shipment> shipments;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;
}
