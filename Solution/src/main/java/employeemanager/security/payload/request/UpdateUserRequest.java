package employeemanager.security.payload.request;

import jakarta.validation.constraints.Size;

public record UpdateUserRequest(@Size(max = 50, min = 2) String name, @Size(max = 50, min = 2) String surname,
                                @Size(max = 50, min = 2) String password1, @Size(max = 50, min = 2) String password2) {

    public UpdateUserRequest(String name, String surname, String password1, String password2) {
        this.name = name;
        this.surname = surname;
        this.password1 = password1;
        this.password2 = password2;
    }

    @Override
    public String name() { return name; }

    @Override
    public String surname() {
        return surname;
    }

    @Override
    public String password1() { return password1; }

    @Override
    public String password2() { return password2; }
}
