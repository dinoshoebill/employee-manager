package employeemanager.services.intf;

import employeemanager.models.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeService {

    Optional<List<Employee>> listAllEmployees(UUID userId);

    Optional<Employee> findById(UUID employeeId);

    Employee createNewEmployee(Employee employee);

    Employee update(Employee employee);

    void delete(Employee employee);

}
