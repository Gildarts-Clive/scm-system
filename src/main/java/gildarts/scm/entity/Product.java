package gildarts.scm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Lombok automatically generates Getters, Setters, and toString methods
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private Double price;
    
    // This tracks how many you actually have
    private Integer currentStock;
    
    // THE TWIST: The Predictive Logic Field
    // If currentStock drops below this number, the system predicts a shortage.
    private Integer reorderThreshold; 
}