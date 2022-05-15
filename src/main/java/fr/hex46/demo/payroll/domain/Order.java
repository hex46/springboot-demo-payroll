package fr.hex46.demo.payroll.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@EqualsAndHashCode @ToString @Getter
public class Order {

    private UUID uuid;
    private String description;
    private Status status;

    public void cancel() throws OrderException {
        if (this.status != Status.IN_PROGRESS)
            throw new OrderException("You can't cancel an order that is in the " + this.status + " status.");
        this.status = Status.CANCELLED;
    }

    public void complete() throws OrderException {
        if (this.status != Status.IN_PROGRESS)
            throw new OrderException("You can't cancel an order that is in the " + this.status + " status.");
        this.status = Status.COMPLETED;
    }
}