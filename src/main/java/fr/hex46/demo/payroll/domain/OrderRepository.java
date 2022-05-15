package fr.hex46.demo.payroll.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    List<Order> findAll();
    Optional<Order> getOne(UUID uuid);
    Order save(Order order);
}
