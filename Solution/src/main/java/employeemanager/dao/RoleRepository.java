package employeemanager.dao;

import employeemanager.models.Role;
import employeemanager.utils.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleName name);

    @Query(value = "SELECT COUNT(role) FROM roles NATURAL JOIN user_roles WHERE role = ?1", nativeQuery = true)
    long getRoleCount(String roleName);
}
