package employeemanager.services.intf;

import employeemanager.models.Company;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICompanyService {

    List<Company> listAllCompanies();

    Optional<Company> findById(UUID companyId);

    Optional<Company> findByName(String companyName);

    Company createNewCompany(Company company);

    Company updateCompany(Company company);

    Company deleteCompany(UUID userId);
}
