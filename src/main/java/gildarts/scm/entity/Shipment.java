package gildarts.scm.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    
    private LocalDate shipmentDate;

    // One-to-Many Relationship: One Shipment has many Items
    // "cascade = CascadeType.ALL" means if you delete a shipment, its items are deleted too.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shipment")
    private List<ShipmentItem> items = new ArrayList<>();
    
    // Helper method to calculate the total cost of the shipment
    // We stream through the items, multiply price * quantity, and sum it up.
    public Double getTotalAmount() {
        if (items == null) return 0.0;
        
        return items.stream()
            .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
            .sum();
    }
}