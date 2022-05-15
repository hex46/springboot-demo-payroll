package fr.hex46.demo.payroll.presentation;

import fr.hex46.demo.payroll.application.ApplicationOrderService;
import fr.hex46.demo.payroll.application.OrderDTOApplication;
import fr.hex46.demo.payroll.domain.service.OrderNotFoundException;
import fr.hex46.demo.payroll.domain.OrderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class OrderRestController {

    private final OrderModelAssembler assembler;
    private final OrderDTOPresentationConverter orderDTOPresentationConverter;
    private final ApplicationOrderService applicationOrderService;

    @Autowired
    OrderRestController(OrderModelAssembler assembler, OrderDTOPresentationConverter orderDTOPresentationConverter, ApplicationOrderService applicationOrderService) {
        this.assembler = assembler;
        this.orderDTOPresentationConverter = orderDTOPresentationConverter;
        this.applicationOrderService = applicationOrderService;
    }

    @GetMapping("/orders")
    ResponseEntity<?> all() {
        List<EntityModel<OrderDTOPresentation>> orders = applicationOrderService.findAll()
                .stream()
                .map(this.orderDTOPresentationConverter::convertFromEntity)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(orders,
                linkTo(methodOn(OrderRestController.class).all()).withSelfRel()));
    }

    @GetMapping("/orders/{uuid}")
    ResponseEntity<?> one(@PathVariable UUID uuid) {
        try {
            OrderDTOApplication orderDTOApplication = applicationOrderService.getOrder(uuid);
            OrderDTOPresentation orderDTOPresentation = this.orderDTOPresentationConverter.convertFromEntity(orderDTOApplication);

            return ResponseEntity.ok(assembler.toModel(orderDTOPresentation));
        } catch (OrderNotFoundException e) {
            return getNotFoundResponse(e);
        }
    }

    @PostMapping("/orders")
    ResponseEntity<?> newOrder(@RequestBody OrderDTOPresentation orderDTOPresentation) {
        OrderDTOApplication newOrder = this.orderDTOPresentationConverter.convertFromDto(orderDTOPresentation);
        applicationOrderService.saveOrder(newOrder);
        return ResponseEntity
                .created(
                        linkTo(methodOn(OrderRestController.class).one(orderDTOPresentation.uuid()))
                                .toUri())
                .body(assembler.toModel(orderDTOPresentation));
    }

    @DeleteMapping("/orders/{uuid}/cancel")
    ResponseEntity<?> cancel(@PathVariable UUID uuid) {
        try {
            OrderDTOApplication orderDTOApplication = applicationOrderService.cancelOrder(uuid);
            OrderDTOPresentation orderDTOPresentation = this.orderDTOPresentationConverter.convertFromEntity(orderDTOApplication);

            return ResponseEntity.ok(assembler.toModel(orderDTOPresentation));
        } catch (OrderException e) {
            return getMethodNotAllowedResponse(e);
        } catch (OrderNotFoundException e) {
            return getNotFoundResponse(e);
        }
    }

    @PutMapping("/orders/{uuid}/complete")
    ResponseEntity<?> complete(@PathVariable UUID uuid) {
        try {
            OrderDTOApplication orderDTOApplication = applicationOrderService.completeOrder(uuid);
            OrderDTOPresentation orderDTOPresentation = this.orderDTOPresentationConverter.convertFromEntity(orderDTOApplication);

            return ResponseEntity.ok(assembler.toModel(orderDTOPresentation));
        } catch (OrderException e) {
            return getMethodNotAllowedResponse(e);
        } catch (OrderNotFoundException e) {
            return getNotFoundResponse(e);
        }
    }

    private ResponseEntity<Problem> getMethodNotAllowedResponse(OrderException e) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail(e.getMessage()));
    }

    private ResponseEntity<Problem> getNotFoundResponse(OrderNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Order not found")
                        .withDetail(e.getMessage()));
    }
}