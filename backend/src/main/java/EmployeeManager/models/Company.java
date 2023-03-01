package EmployeeManager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(	name = "companies",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID companyId;

    @NotBlank
    @Size(max = 50)
    private String name;

    public Company() { }

    public Company(String name) {
        this.name = name;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(companyId, company.companyId) && Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
