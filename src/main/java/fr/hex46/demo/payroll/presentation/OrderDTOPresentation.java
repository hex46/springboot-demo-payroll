package fr.hex46.demo.payroll.presentation;

import fr.hex46.demo.payroll.domain.Status;

import java.util.UUID;

public record OrderDTOPresentation(UUID uuid, String description, Status status) {
}
