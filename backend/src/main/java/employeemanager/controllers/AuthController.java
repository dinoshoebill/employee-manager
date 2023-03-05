package employeemanager.controllers;

import employeemanager.dao.UserRepository;
import employeemanager.models.User;
import employeemanager.security.jwt.JwtUtilization;
import employeemanager.security.payload.request.LoginRequest;
import employeemanager.security.payload.request.SignupRequest;
import employeemanager.security.payload.response.JwtResponse;
import employeemanager.security.userdetails.SecurityUserDetails;
import employeemanager.services.impl.UserService;
import employeemanager.utils.CreateUserTemplate;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtilization jwtUtilization;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtilization jwtUtilization, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtilization = jwtUtilization;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtilization.generateJwtToken(authentication);

        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        Optional<? extends GrantedAuthority> role = userDetails.getAuthorities().stream().findFirst();

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        CreateUserTemplate userRequest = new CreateUserTemplate(
                signUpRequest.getName().trim(),
                signUpRequest.getSurname().trim(),
                signUpRequest.getEmail().trim(),
                signUpRequest.getRole().trim(),
                signUpRequest.getPassword().trim());

        User user = userService.createNewUser(userRequest);
        return ResponseEntity.ok(user);
    }
}