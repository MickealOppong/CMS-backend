package opp.mic.payroll.repository;

import opp.mic.payroll.model.ProductSKU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSKURepository extends JpaRepository<ProductSKU,Long> {

}