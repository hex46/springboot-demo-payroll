package fr.hex46.demo.payroll.infrastructure;

import fr.hex46.demo.payroll.domain.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(JpaOrderRepository jpaOrderRepository) {
    return args -> {
      jpaOrderRepository.save(new OrderJpa(UUID.randomUUID(), "MacBook Pro", Status.COMPLETED));
      jpaOrderRepository.save(new OrderJpa(UUID.randomUUID(), "iPhone", Status.IN_PROGRESS));

      jpaOrderRepository.findAll().forEach(order -> log.info("Preloaded " + order));
    };
    }
}
