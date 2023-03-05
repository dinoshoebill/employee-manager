package employeemanager.services.intf;

import employeemanager.models.User;
import employeemanager.security.payload.request.CreateUserRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    List<User> listAllUsers();

    Optional<User> findByUserId(UUID userId);

    User createNewUser(CreateUserRequest user);

    User updateUser(User user);

    User deleteUser(UUID userId);
}
