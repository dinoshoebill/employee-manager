package employeemanager.services.intf;

import employeemanager.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    List<User> listAllUsers();

    User createNewUser(User user);

    User update(User user);

    void delete(User user);

    Optional<User> findById(UUID id);

}
