package fr.hex46.demo.payroll.infrastructure;

import fr.hex46.demo.payroll.domain.Order;
import fr.hex46.demo.payroll.shared.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderJpaConverter extends Converter<OrderJpa, Order> {

    public OrderJpaConverter() {
        super(OrderJpaConverter::convertToEntity, OrderJpaConverter::convertToDto);
    }

    private static OrderJpa convertToDto(Order order) {
        return new OrderJpa(
                order.getUuid(),
                order.getDescription(),
                order.getStatus());
    }

    private static Order convertToEntity(OrderJpa orderJpa) {
        return new Order(
                orderJpa.getUuid(),
                orderJpa.getDescription(),
                orderJpa.getStatus());
    }
}
