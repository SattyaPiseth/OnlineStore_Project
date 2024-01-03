package co.devkh.onlinestore.reviewonlinestore.api.shipment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shipment_type")
public class ShipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,length = 150,name = "type_name")
    private String typeName;
}
