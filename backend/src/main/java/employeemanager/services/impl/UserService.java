package employeemanager.services.impl;

import employeemanager.dao.UserRepository;
import employeemanager.models.User;
import employeemanager.services.intf.IUserService;
import employeemanager.utils.CreateUserTemplate;
import employeemanager.utils.Role;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUserId(UUID userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User createNewUser(CreateUserTemplate userRequest) {

        validate(userRequest);

        User newUser = createNew(userRequest);

        if(userRepository.existsByEmail(userRequest.getEmail()))
            throw new RequestRejectedException("Email is already taken");

        if(Arrays.stream(Role.values()).noneMatch(role -> role.equals(Role.valueOf(userRequest.getRole()))))
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
            throw new RequestRejectedException("User not found");

        userRepository.delete(user.get());
        return user.get();
    }

    private void validate(CreateUserTemplate userRequest) {
        Assert.notNull(userRequest, "User object must be given");
        Assert.notNull(userRequest.getName(), "Name must be given");
        Assert.notNull(userRequest.getSurname(), "Surname must be given");
        Assert.notNull(userRequest.getEmail(), "Email must be given");
        Assert.notNull(Role.valueOf(userRequest.getRole()), "Role must be given");
        Assert.notNull(userRequest.getPassword(), "Password must be given");
    }

    private User createNew(CreateUserTemplate userRequest) {
        return new User(userRequest.getName(),
                userRequest.getSurname(),
                userRequest.getEmail(),
                Role.valueOf(userRequest.getRole()),
                passwordEncoder.encode(userRequest.getPassword()));
    }
}
