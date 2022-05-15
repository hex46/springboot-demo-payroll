package fr.hex46.demo.payroll.infrastructure;

import fr.hex46.demo.payroll.domain.Order;
import fr.hex46.demo.payroll.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class H2OrderRepository implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final OrderJpaConverter orderJpaConverter;

    @Autowired
    public H2OrderRepository(JpaOrderRepository jpaOrderRepository, OrderJpaConverter orderJpaConverter) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.orderJpaConverter = orderJpaConverter;
    }

    @Override
    public List<Order> findAll() {
        List<OrderJpa> ordersJpa = this.jpaOrderRepository.findAll();
        return this.orderJpaConverter.createFromDtos(ordersJpa);
    }

    @Override
    public Optional<Order> getOne(UUID uuid) {
        try {
            OrderJpa order = this.jpaOrderRepository.getById(uuid);
            return Optional.of(this.orderJpaConverter.convertFromDto(order));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public Order save(Order order) {
        OrderJpa orderDTO = this.orderJpaConverter.convertFromEntity(order);
        OrderJpa orderDTODTOSaved = this.jpaOrderRepository.save(orderDTO);

        return this.orderJpaConverter.convertFromDto(orderDTODTOSaved);
    }
}
