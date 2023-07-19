package employeemanager.dao;

import employeemanager.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByIdentificator(String identificator);

    boolean existsByCompanyEmail(String companyEmail);

    @Query(value = "SELECT * FROM employees WHERE employees.user_Id = ?1", nativeQuery = true)
    Optional<List<Employee>> getUserEmployees(UUID userId);
}
