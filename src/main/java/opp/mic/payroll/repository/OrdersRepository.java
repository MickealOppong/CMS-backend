package opp.mic.payroll.repository;

import opp.mic.payroll.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order,Long> {
}
