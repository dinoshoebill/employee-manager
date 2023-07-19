package employeemanager.controllers;

import employeemanager.exceptions.RequestDeniedException;
import employeemanager.models.Employee;
import employeemanager.models.User;
import employeemanager.security.payload.request.CreateEmployeeRequest;
import employeemanager.security.payload.request.UpdateEmployeeRequest;
import employeemanager.security.userdetails.SecurityUserDetails;
import employeemanager.services.impl.EmployeeService;
import employeemanager.services.impl.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/employees")
public class EmployeeController {

    private final UserService userService;
    private final EmployeeService employeeService;

    public EmployeeController(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('EMPLOYEE_ADMIN')")
    public List<Employee> listEmployees() {

        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> manager = userService.findById(userDetails.getUserId());

        Optional<List<Employee>> employees = employeeService.listAllEmployees(manager.get().getUserId());

        if(employees.isEmpty())
            return new ArrayList<>();
        else
            return employees.get();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE_ADMIN')")
    public Employee getEmployee(@PathVariable("id") UUID id) {

        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Employee> employee = employeeService.findById(id);

        if(employee.isEmpty())
            throw new EntityNotFoundException("Employee with ID: '" + id + "' not found.");
        if(!employee.get().getManager().getUserId().equals(userDetails.getUserId()))
            throw new EntityNotFoundException("Employee with ID: '" + id + "' not found.");

        return employee.get();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE_ADMIN')")
    public Employee updateEmployee(@PathVariable("id") UUID id, @RequestBody UpdateEmployeeRequest employee) {

        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Employee> updateEmployee = employeeService.findById(id);

        if(updateEmployee.isEmpty())
            throw new EntityNotFoundException("Employee with ID: '" + id + "' not found.");
        if(!updateEmployee.get().getManager().getUserId().equals(userDetails.getUserId()))
            throw new EntityNotFoundException("Employee with ID: '" + id + "' not found.");

        if(employee.identificator() == null && employee.name() == null && employee.surname() == null && employee.companyEmail() == null)
            throw new IllegalArgumentException("No valid arguments given.");

        if(employee.identificator() != null && !employee.identificator().isBlank())
            updateEmployee.get().setIdentificator(employee.identificator().trim());

        if(employee.name() != null && !employee.name().isBlank())
            updateEmployee.get().setName(employee.name().trim());

        if(employee.surname() != null && !employee.surname().isBlank())
            updateEmployee.get().setSurname(employee.surname().trim());

        if(employee.companyEmail() != null && !employee.companyEmail().isBlank())
            updateEmployee.get().setCompanyEmail(employee.companyEmail().trim());

        return employeeService.update(updateEmployee.get());

    }

    @PostMapping("")
    @PreAuthorize("hasRole('EMPLOYEE_ADMIN')")
    public Employee createEmployee(@RequestBody CreateEmployeeRequest createEmployeeRequest) {

        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> manager = userService.findById(userDetails.getUserId());

        Employee employee = new Employee(createEmployeeRequest.identificator(), createEmployeeRequest.name().trim(), createEmployeeRequest.surname().trim(), createEmployeeRequest.companyEmail().trim(), manager.get());

        return employeeService.createNewEmployee(employee);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE_ADMIN')")
    public String deleteEmployee(@PathVariable("id") UUID id) {

        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Employee> employee = employeeService.findById(id);

        if(employee.isEmpty())
            throw new EntityNotFoundException("Employee with identificator: '" + id + "' not found.");
        else if(!employee.get().getManager().getUserId().equals(userDetails.getUserId()))
            throw new EntityNotFoundException("Employee with identificator: '" + id + "' not found.");

        employeeService.delete(employee.get());

        return "Employee deleted successfully.";
    }
}
