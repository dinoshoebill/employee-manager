package EmployeeManager.services.intf;

import EmployeeManager.models.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeService {

    List<Employee> listAllEmployees();

    Optional<Employee> findByIdentificator(Long identificator);

    Optional<Employee> findById(UUID employeeId);

    Employee createNewEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Employee deleteEmployee(UUID employeeId);
}
