package gildarts.scm.repository;

import gildarts.scm.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    // We don't need any custom queries for Shipments right now, 
    // the standard ones (save, find, delete) are enough.
}