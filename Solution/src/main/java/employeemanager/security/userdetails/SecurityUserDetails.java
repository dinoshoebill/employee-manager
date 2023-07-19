package employeemanager.security.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import employeemanager.models.Role;
import employeemanager.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class SecurityUserDetails implements UserDetails {

    private final UUID userId;

    private final String username;

    private final String email;

    private final Set<Role> role;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public SecurityUserDetails(UUID userId, String username, String email, String password, Set<Role> role,
                               Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.authorities = authorities;
    }

    public static SecurityUserDetails buildUser(User user) {

        List<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role : user.getRole())
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));

        return new SecurityUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRole() { return role; }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SecurityUserDetails user = (SecurityUserDetails) o;
        return Objects.equals(userId, user.userId);
    }
}
