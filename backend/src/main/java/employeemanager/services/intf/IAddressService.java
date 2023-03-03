package employeemanager.services.intf;

import employeemanager.models.Address;
import employeemanager.models.Company;
import employeemanager.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAddressService extends JpaRepository<Address, UUID> {

    Optional<Company> findCompanyByAddress(Address address);

    Optional<Address> findyById(UUID addressId);
}
