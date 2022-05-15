package fr.hex46.demo.payroll.application;

import fr.hex46.demo.payroll.domain.Order;
import fr.hex46.demo.payroll.shared.Converter;

public class OrderDTOApplicationConverter extends Converter<OrderDTOApplication, Order> {

    public OrderDTOApplicationConverter() {
        super(OrderDTOApplicationConverter::convertToEntity, OrderDTOApplicationConverter::convertToDto);
    }

    private static OrderDTOApplication convertToDto(Order order) {
        return new OrderDTOApplication(
                order.getUuid(),
                order.getDescription(),
                order.getStatus());
    }

    private static Order convertToEntity(OrderDTOApplication OrderDTOApplication) {
        return new Order(
                OrderDTOApplication.uuid(),
                OrderDTOApplication.description(),
                OrderDTOApplication.status());
    }
}
