package fr.hex46.demo.payroll.infrastructure.rest.order;


public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Could not find order " + id);
    }
}
