package employeemanager.security.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record CreateEmployeeRequest(@Size(max = 20, min = 2) String identificator, @Size(max = 50, min = 2) String name,
                                    @Size(max = 50, min = 2) String surname,
                                    @Size(max = 70, min = 2) @Email String companyEmail) {

    public CreateEmployeeRequest(String identificator, String name, String surname, String companyEmail) {
        this.identificator = identificator;
        this.name = name;
        this.surname = surname;
        this.companyEmail = companyEmail;
    }

    @Override
    public String identificator() {
        return identificator;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String surname() {
        return surname;
    }

    @Override
    public String companyEmail() {
        return companyEmail;
    }


}
