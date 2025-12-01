package gildarts.scm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ShipmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many Items can belong to One Product (e.g., many shipments can have Laptops)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Many Items belong to One Shipment
    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    private Integer quantity;
}