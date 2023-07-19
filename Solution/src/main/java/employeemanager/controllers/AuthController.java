package employeemanager.controllers;

import employeemanager.dao.UserRepository;
import employeemanager.security.jwt.JwtUtilization;
import employeemanager.security.payload.request.LoginRequest;
import employeemanager.security.payload.response.JwtResponse;
import employeemanager.services.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtUtilization jwtUtilization;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtilization jwtUtilization, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilization = jwtUtilization;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtilization.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}