package employeemanager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID employeeId;

    @NotBlank
    @Size(max = 20, min = 2)
    @Column(unique = true)
    private String identificator;

    @NotBlank
    @Size(max = 50, min = 2)
    private String name;

    @NotBlank
    @Size(max = 50, min = 2)
    private String surname;

    @NotBlank
    @Size(max = 70, min = 2)
    @Email
    @Column(unique = true)
    private String companyEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User manager;

    public Employee(String identificator, String name, String surname, String companyEmail, User manager) {
        this.identificator = identificator;
        this.name = name;
        this.surname = surname;
        this.companyEmail = companyEmail;
        this.manager = manager;
    }

    public Employee() {

    }

    public UUID getEmployeeId() { return employeeId; }

    public String getIdentificator() { return identificator; }

    public void setIdentificator(String identificator) { this.identificator = identificator; }

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

    public User getManager() {
        return manager;
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
