package EmployeeManager.services.intf;

import EmployeeManager.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {

    List<User> listAllUsers();

    Optional<User> findById(UUID userId);

    User createNewUser(User user);

    User updateUser(User user);

    User deleteUser(UUID userId);
}
