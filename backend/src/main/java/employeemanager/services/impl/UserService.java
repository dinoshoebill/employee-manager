package employeemanager.services.impl;

import employeemanager.dao.UserRepository;
import employeemanager.models.User;
import employeemanager.security.payload.request.CreateUserRequest;
import employeemanager.services.intf.IUserService;
import employeemanager.utils.Role;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUserId(UUID userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User createNewUser(CreateUserRequest user) {

        validate(user);

        User newUser = createNew(user);

        if(userRepository.existsByEmail(user.getEmail()))
            throw new RequestRejectedException("Email is already taken");

        if(Arrays.stream(Role.values()).noneMatch(role -> role.equals(user.getRole())))
            throw new RequestRejectedException("No such role defined");

        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(UUID userId) {

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty())
            throw new RequestRejectedException("User not found with ID: " + userId);

        userRepository.delete(user.get());
        return user.get();
    }

    private void validate(CreateUserRequest user) {
        Assert.notNull(user, "User object must be given");
        Assert.notNull(user.getName(), "Name must be given");
        Assert.notNull(user.getSurname(), "Surname must be given");
        Assert.notNull(user.getEmail(), "Email must be given");
        Assert.notNull(user.getRole(), "Role must be given");
        Assert.notNull(user.getPassword(), "Password must be given");
    }

    private User createNew(CreateUserRequest user) {
        return new User(user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getRole(),
                passwordEncoder.encode(user.getPassword()));
    }
}
