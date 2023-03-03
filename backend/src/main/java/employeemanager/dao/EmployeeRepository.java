package employeemanager.dao;

import employeemanager.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Optional<Employee> findById(UUID employeeId);

    Optional<Employee> findByIdentificator(UUID identificator);

    Optional<Employee> findByEmail(String email);

    Boolean existsByEmail(String email);
}
