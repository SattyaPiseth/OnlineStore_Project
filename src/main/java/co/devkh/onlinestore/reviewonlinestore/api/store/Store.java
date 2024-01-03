package co.devkh.onlinestore.reviewonlinestore.api.store;

import co.devkh.onlinestore.reviewonlinestore.api.inventory.Inventory;
import co.devkh.onlinestore.reviewonlinestore.api.shipment.Shipment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true,nullable = false)
    private String uuid;
    @Column(unique = true)
    private String store_name;
    @Column(length = 25,nullable = false)
    private String phone;
    @Column(length = 100,unique = true)
    private String email;
    private String street;
    @Column(length = 80)
    private String city;
    @Column(length = 50)
    private String state;
    @Column(length = 10)
    private String zip_code;

    @OneToMany(mappedBy = "store")
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "store")
    private List<Shipment> shipments;
}
