package opp.mic.payroll.repository;

import opp.mic.payroll.model.ProductSKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSKURepository extends JpaRepository<ProductSKU,Long> {
    Optional<ProductSKU> findBySkuValue(String sku);
}
