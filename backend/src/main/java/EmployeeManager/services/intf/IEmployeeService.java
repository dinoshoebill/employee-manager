package EmployeeManager.services.intf;

import EmployeeManager.models.User;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    
    List<User> listAllUsers();

    Optional<User> findByIdentificator(Long identificator);

    Optional<User> findById(Long userId);

    User createNewUser(User user);

    User updateUser(User user);

    User deleteUser(Long userId);
}
