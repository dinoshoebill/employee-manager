package employeemanager.services.impl;

import employeemanager.dao.EmployeeRepository;
import employeemanager.models.Employee;
import employeemanager.services.intf.IEmployeeService;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Optional<List<Employee>> listAllEmployees(UUID userId) { return employeeRepository.getUserEmployees(userId); }

    @Override
    public Optional<Employee> findById(UUID employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Employee createNewEmployee(Employee employee) {

        if(employeeRepository.existsByIdentificator(employee.getIdentificator()))
            throw new RequestRejectedException("Identificator is already taken");
        else if(employeeRepository.existsByCompanyEmail(employee.getCompanyEmail()))
            throw new RequestRejectedException("Company email is already taken");

        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }
}
