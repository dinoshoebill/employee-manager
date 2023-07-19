package employeemanager.initializer;

import employeemanager.dao.EmployeeRepository;
import employeemanager.dao.RoleRepository;
import employeemanager.dao.UserRepository;
import employeemanager.models.Employee;
import employeemanager.models.Role;
import employeemanager.models.User;
import employeemanager.utils.RoleName;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DatabaseInitializer {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmployeeRepository employeeRepository;

    private final RoleRepository roleRepository;

    public DatabaseInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    private void initializeDatabase() {

        Role roleAdmin = new Role(RoleName.ROLE_APP_ADMIN);
        Role roleEmpAdmin = new Role(RoleName.ROLE_EMPLOYEE_ADMIN);

        roleRepository.save(roleAdmin);
        roleRepository.save(roleEmpAdmin);

        User admin1 = new User("App", "Admin", "app.admin1@gmail.com", new HashSet<>(Arrays.asList(roleAdmin)), passwordEncoder.encode("password"));
        User admin2 = new User("App", "Admin", "app.admin2@gmail.com", new HashSet<>(Arrays.asList(roleAdmin)), passwordEncoder.encode("password"));

        User empAdmin1 = new User("Employee", "Admin", "employee.admin1@gmail.com", new HashSet<>(Arrays.asList(roleEmpAdmin)), passwordEncoder.encode("password"));
        User empAdmin2 = new User("Employee", "Admin", "employee.admin2@gmail.com", new HashSet<>(Arrays.asList(roleEmpAdmin)), passwordEncoder.encode("password"));

        Employee emp1 = new Employee("identificator1", "Name", "Surname", "employee1@gmail.com", empAdmin1);
        Employee emp2 = new Employee("identificator2", "Name", "Surname", "employee2@gmail.com", empAdmin1);

        if(!userRepository.existsByEmail("app.admin1@gmail.com"))
            userRepository.save(admin1);
        if(!userRepository.existsByEmail("app.admin2@gmail.com"))
            userRepository.save(admin2);
        if(!userRepository.existsByEmail("employee1.admin@gmail.com"))
            userRepository.save(empAdmin1);
        if(!userRepository.existsByEmail("employee2.admin@gmail.com"))
            userRepository.save(empAdmin2);

        if(!employeeRepository.existsByCompanyEmail("employee1@gmail.com"))
            employeeRepository.save(emp1);
        if(!employeeRepository.existsByCompanyEmail("employee2@gmail.com"))
            employeeRepository.save(emp2);
    }
}
