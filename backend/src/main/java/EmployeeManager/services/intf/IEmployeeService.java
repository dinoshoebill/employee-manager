package EmployeeManager.services.intf;

import EmployeeManager.models.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {

    List<Employee> listAllEmployees();

    Optional<Employee> findByIdentificator(Long identificator);

    Optional<Employee> findById(Long employeeId);

    Employee createNewEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    Employee deleteEmployee(Long employeeId);
}
