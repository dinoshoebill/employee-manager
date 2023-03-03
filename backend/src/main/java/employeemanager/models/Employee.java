package employeemanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(	name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "identificator"),
                @UniqueConstraint(columnNames = "companyEmail")
        })
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID employeeId;

    @NotBlank
    @Size(max = 20)
    private String identificator;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String surname;

    @NotBlank
    @Size(max = 70)
    @Email
    private String companyEmail;

    @NotBlank
    private long salary;

    public Employee() { }

    public Employee(String identificator, String name, String surname, String companyEmail, long salary) {
        this.identificator = identificator;
        this.name = name;
        this.surname = surname;
        this.companyEmail = companyEmail;
        this.salary = salary;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    private String getIdentificator() { return identificator; }

    private  void setIdentificator(String identificator) { this.identificator = identificator; }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() { return surname; }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) && Objects.equals(identificator, employee.identificator) && Objects.equals(name, employee.name) && Objects.equals(surname, employee.surname) && Objects.equals(companyEmail, employee.companyEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, identificator, name, surname, companyEmail);
    }
}
