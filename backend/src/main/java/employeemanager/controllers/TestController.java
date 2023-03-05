package employeemanager.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public content route.";
    }

    @GetMapping("/employee_admin")
    @PreAuthorize("hasAuthority('EMPLOYEE_ADMIN')")
    public String employeeAdminAccess() {
        return "Employee admin content.";
    }

    @GetMapping("/app_admin")
    @PreAuthorize("hasAuthority('APP_ADMIN')")
    public String appAdminAccess() {
        return "App admin content.";
    }
}
