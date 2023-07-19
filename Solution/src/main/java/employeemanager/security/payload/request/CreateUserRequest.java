package employeemanager.security.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateUserRequest(@NotBlank @Size(max = 50, min = 2) String name,
                                @NotBlank @Size(max = 50, min = 2) String surname,
                                @NotBlank @Size(max = 70, min = 2) @Email String email,
                                @NotBlank @Size(max = 50, min = 8) String password, @NotBlank Set<String> role) {

    public CreateUserRequest(String name, String surname, String email, String password, Set<String> role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
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
    public String email() {
        return email;
    }

    @Override
    public Set<String> role() {
        return role;
    }

    @Override
    public String password() {
        return password;
    }
}
