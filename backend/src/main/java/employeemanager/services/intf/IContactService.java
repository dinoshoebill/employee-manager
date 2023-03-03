package employeemanager.services.intf;

import employeemanager.models.ContactInfo;
import employeemanager.models.Employee;
import employeemanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IContactService extends JpaRepository<ContactInfo, UUID> {

    Optional<Employee> findEmployeebyContactInfo(ContactInfo contactInfo);

    Optional<User> findUserByContactInfo(ContactInfo contactInfo);

    Optional<ContactInfo> findyById(UUID contactInfo);

}
