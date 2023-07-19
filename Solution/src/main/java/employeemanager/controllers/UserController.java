package employeemanager.controllers;

import employeemanager.dao.RoleRepository;
import employeemanager.exceptions.RequestDeniedException;
import employeemanager.models.Role;
import employeemanager.models.User;
import employeemanager.security.payload.request.CreateUserRequest;
import employeemanager.security.payload.request.UpdateUserRequest;
import employeemanager.security.userdetails.SecurityUserDetails;
import employeemanager.services.impl.UserService;
import employeemanager.utils.RoleName;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('APP_ADMIN')")
    public List<User> listUsers() {
        return userService.listAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('APP_ADMIN')")
    public User getUser(@PathVariable("id") UUID id) {

        Optional<User> user = userService.findById(id);

        if (user.isEmpty())
            throw new EntityNotFoundException("User with ID: '" + id + "' not found.");

        return user.get();
    }

    @GetMapping("/account")
    @PreAuthorize("hasRole('APP_ADMIN') or hasRole('EMPLOYEE_ADMIN')")
    public User getUserInfo() {

        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> user = userService.findById(userDetails.getUserId());

        return user.get();
    }

    @PostMapping("")
    @PreAuthorize("hasRole('APP_ADMIN')")
    public User createUser(@RequestBody CreateUserRequest createUserRequest) {

        User user = new User(
                createUserRequest.name().trim(),
                createUserRequest.surname().trim(),
                createUserRequest.email().trim(),
                setRoles(createUserRequest.role()),
                passwordEncoder.encode(createUserRequest.password()));

        return userService.createNewUser(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('APP_ADMIN')")
    public User updateUser(@PathVariable("id") UUID id, @RequestBody UpdateUserRequest user) {

        Optional<User> updateUser = userService.findById(id);

        return setNewInfo(user, updateUser);
    }

    @PutMapping("/account")
    @PreAuthorize("hasRole('APP_ADMIN') or hasRole('EMPLOYEE_ADMIN')")
    public User updateUserInfo(@RequestBody UpdateUserRequest user) {

        SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<User> updateUser = userService.findById(userDetails.getUserId());

        return setNewInfo(user, updateUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('APP_ADMIN')")
    public String deleteUser(@PathVariable("id") UUID id) {

        Optional<User> user = userService.findById(id);

        if (user.isEmpty())
            throw new EntityNotFoundException("User with id: '" + id + "' not found.");

        for(Role role : user.get().getRole()) {
            if(role.getRole() == RoleName.ROLE_APP_ADMIN)
                if(roleRepository.getRoleCount(role.getRole().name()) < 2)
                    throw new RequestDeniedException("Can't delete only application admin.");
        }

        userService.delete(user.get());

        return "User deleted successfully.";
    }

    private Set<Role> setRoles(Set<String> roles) {

        Set<Role> newRoles = new HashSet<>();

        for(String role : roles) {
            if(Arrays.stream(RoleName.values()).noneMatch(roleName -> roleName.name().equals(role.toUpperCase(Locale.ROOT)))) {
                throw new IllegalArgumentException("Role: '" + role.toUpperCase() + "' is not defined.");
            } else {
                Optional<Role> addRole = roleRepository.findByRole(RoleName.valueOf(role.toUpperCase()));

                if(addRole.isEmpty())
                    throw new IllegalArgumentException("Role: '" + role.toUpperCase() + "' is not defined.");
                else
                    newRoles.add(addRole.get());
            }
        }

        return newRoles;
    }

    private User setNewInfo(UpdateUserRequest user, Optional<User> updateUser) {

        if (user.name() == null && user.surname() == null && user.password1() == null && user.password2() == null)
            throw new IllegalArgumentException("No valid arguments given.");

        if (user.name() != null && !user.name().isBlank())
            updateUser.get().setName(user.name().trim());

        if (user.surname() != null && !user.surname().isBlank())
            updateUser.get().setSurname(user.surname().trim());

        if((user.password1() == null && user.password2() != null) || (user.password1() != null && user.password2() == null))
            throw new IllegalArgumentException("Passwords do not match.");

        if (user.password1() != null && user.password2() != null) {
            if (!user.password1().isBlank() && !user.password2().isBlank()) {
                if (user.password1().equals(user.password2())) {
                    updateUser.get().setPassword(passwordEncoder.encode(user.password1().trim()));
                } else {
                    throw new IllegalArgumentException("Passwords do not match.");
                }
            } else {
                throw new IllegalArgumentException("Passwords do not match.");
            }
        }

        return userService.update(updateUser.get());
    }
}
