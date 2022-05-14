package fr.hex46.demo.payroll.infrastructure.db;

import fr.hex46.demo.payroll.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
