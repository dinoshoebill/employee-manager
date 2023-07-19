package employeemanager.services.impl;

import employeemanager.dao.UserRepository;
import employeemanager.models.User;
import employeemanager.services.intf.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(UUID id) { return userRepository.findById(id); }

    @Override
    public User createNewUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public User update(User user) { return userRepository.save(user); }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

}
