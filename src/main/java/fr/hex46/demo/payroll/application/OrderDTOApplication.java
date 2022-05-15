package fr.hex46.demo.payroll.application;

import fr.hex46.demo.payroll.domain.Status;

import java.util.UUID;

public record OrderDTOApplication(UUID uuid, String description, Status status) {
}
