package fr.hex46.demo.payroll.infrastructure;

import fr.hex46.demo.payroll.domain.Status;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "customer_order")
@NoArgsConstructor @AllArgsConstructor // Just for LoadDatabase
@Getter @Setter @EqualsAndHashCode
@ToString
public class OrderJpa {
    @Id @GeneratedValue(generator = "uuid2")
    private UUID uuid;
    private String description;
    private Status status;
}
