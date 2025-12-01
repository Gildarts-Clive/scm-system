package gildarts.scm.repository;

import gildarts.scm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Custom Query Method
    // Spring Data JPA automatically turns this method name into SQL!
    // It finds all products where currentStock is less than the threshold provided.
    List<Product> findByCurrentStockLessThan(Integer threshold);
}