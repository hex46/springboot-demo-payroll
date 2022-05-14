package fr.hex46.demo.payroll.infrastructure.db;

import fr.hex46.demo.payroll.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
