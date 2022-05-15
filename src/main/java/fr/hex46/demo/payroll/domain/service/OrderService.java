package fr.hex46.demo.payroll.domain.service;

import fr.hex46.demo.payroll.application.OrderDTOApplication;
import fr.hex46.demo.payroll.domain.OrderException;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDTOApplication saveOrder(OrderDTOApplication orderApplication);

    OrderDTOApplication completeOrder(UUID uuid) throws OrderException, OrderNotFoundException;
    OrderDTOApplication cancelOrder(UUID uuid) throws OrderException, OrderNotFoundException;

    List<OrderDTOApplication> findAll();
    OrderDTOApplication getOrder(UUID uuid) throws OrderNotFoundException;
}

