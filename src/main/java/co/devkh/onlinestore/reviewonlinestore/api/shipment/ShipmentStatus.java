package co.devkh.onlinestore.reviewonlinestore.api.shipment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shipment_status")
public class ShipmentStatus
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @ManyToOne
    @JoinColumn(name = "status_catalog_id")
    private StatusCatalog statusCatalog;

    @Column(name = "status_time",nullable = false)
    private LocalDateTime statusTime;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
