package fr.hex46.demo.payroll.presentation;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import fr.hex46.demo.payroll.domain.Status;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class OrderModelAssembler implements RepresentationModelAssembler<OrderDTOPresentation, EntityModel<OrderDTOPresentation>> {

    @SneakyThrows
    @Override
    public EntityModel<OrderDTOPresentation> toModel(OrderDTOPresentation order) {

        // Unconditional links to single-item resource and aggregate root
        EntityModel<OrderDTOPresentation> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderRestController.class).one(order.uuid())).withSelfRel(),
                linkTo(methodOn(OrderRestController.class).all()).withRel("orders"));

        // Conditional links based on state of the order
        // Delete this business rule?
        if (order.status() == Status.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderRestController.class).cancel(order.uuid())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderRestController.class).complete(order.uuid())).withRel("complete"));
        }

        return orderModel;
    }
}