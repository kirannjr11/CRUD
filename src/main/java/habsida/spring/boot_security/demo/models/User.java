package habsida.spring.boot_security.demo.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)

    private String firstName;
    private String lastName;
    private String country;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "roles_id")})
    private Set<Role> roles;

    public User(@NonNull String firstName, String lastName, String country, @NonNull String password, @NonNull Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getFirstName();
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

}
