package fr.hex46.demo.payroll.configuration;

import fr.hex46.demo.payroll.application.ApplicationOrderService;
import fr.hex46.demo.payroll.application.OrderDTOApplicationConverter;
import fr.hex46.demo.payroll.infrastructure.H2OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayrollConfiguration {
    private final H2OrderRepository orderRepository;

    @Autowired
    public PayrollConfiguration(H2OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Bean
    public OrderDTOApplicationConverter orderDTOApplicationConverter() {
        return new OrderDTOApplicationConverter();
    }

    @Bean
    @Autowired
    public ApplicationOrderService applicationOrderService(OrderDTOApplicationConverter orderDTOApplicationConverter) {
        return new ApplicationOrderService(this.orderRepository, orderDTOApplicationConverter);
    }
}
