package fr.hex46.demo.payroll.domain.service;


import java.util.UUID;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(UUID uuid) {
        super("Could not find order " + uuid);
    }
}
