package fr.hex46.demo.payroll.application;

import fr.hex46.demo.payroll.domain.*;
import fr.hex46.demo.payroll.domain.OrderException;
import fr.hex46.demo.payroll.domain.service.OrderNotFoundException;
import fr.hex46.demo.payroll.domain.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ApplicationOrderService implements OrderService {

    private final OrderRepository repository;
    private final OrderDTOApplicationConverter orderDTOApplicationConverter;

    public ApplicationOrderService(OrderRepository repository, OrderDTOApplicationConverter orderDTOApplicationConverter) {
        this.repository = repository;
        this.orderDTOApplicationConverter = orderDTOApplicationConverter;
    }

    @Override
    public OrderDTOApplication getOrder(UUID uuid) throws OrderNotFoundException {
        Optional<Order> orderOptional = repository.getOne(uuid);

        if (orderOptional.isEmpty())
            throw new OrderNotFoundException(uuid);

        return orderDTOApplicationConverter.convertFromEntity(
                orderOptional.get());
    }

    @Override
    public OrderDTOApplication saveOrder(OrderDTOApplication orderApplication) {
        Order order = orderDTOApplicationConverter.convertFromDto(orderApplication);
        order = this.repository.save(order);
        return orderDTOApplicationConverter.convertFromEntity(order);
    }

    @Override
    public OrderDTOApplication completeOrder(UUID uuid) throws OrderNotFoundException, OrderException {
        Order order = gerOrderByUUIDAndConvertIt(uuid);
        order.complete();
        return convertAndSaveOrder(order);
    }

    @Override
    public OrderDTOApplication cancelOrder(UUID uuid) throws OrderNotFoundException, OrderException {
        Order order = gerOrderByUUIDAndConvertIt(uuid);
        order.cancel();
        return convertAndSaveOrder(order);
    }

    @Override
    public List<OrderDTOApplication> findAll() {
        return this.repository.findAll()
                .stream()
                .map(orderDTOApplicationConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    private OrderDTOApplication convertAndSaveOrder(Order order) {
        OrderDTOApplication orderDTOApplication = orderDTOApplicationConverter.convertFromEntity(order);
        return this.saveOrder(orderDTOApplication);
    }

    private Order gerOrderByUUIDAndConvertIt(UUID uuid) throws OrderNotFoundException {
        OrderDTOApplication orderDTOApplication = this.getOrder(uuid);
        return orderDTOApplicationConverter.convertFromDto(orderDTOApplication);
    }
}
