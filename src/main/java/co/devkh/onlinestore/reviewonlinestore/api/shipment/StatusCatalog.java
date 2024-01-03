package co.devkh.onlinestore.reviewonlinestore.api.shipment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "status_catalog")
public class StatusCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "status_name",nullable = false,unique = true)
    private String statusName;

    @OneToMany(mappedBy = "statusCatalog")
    private List<ShipmentStatus> shipmentStatuses;
}
