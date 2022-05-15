package fr.hex46.demo.payroll.presentation;

import fr.hex46.demo.payroll.application.OrderDTOApplication;
import fr.hex46.demo.payroll.domain.Order;
import fr.hex46.demo.payroll.shared.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderDTOPresentationConverter extends Converter<OrderDTOPresentation, OrderDTOApplication> {

    public OrderDTOPresentationConverter() {
        super(OrderDTOPresentationConverter::convertToEntity, OrderDTOPresentationConverter::convertToDto);
    }

    private static OrderDTOPresentation convertToDto(OrderDTOApplication order) {
        return new OrderDTOPresentation(
                order.uuid(),
                order.description(),
                order.status());
    }

    private static OrderDTOApplication convertToEntity(OrderDTOPresentation orderDTOPresentation) {
        return new OrderDTOApplication(
                orderDTOPresentation.uuid(),
                orderDTOPresentation.description(),
                orderDTOPresentation.status());
    }
}
