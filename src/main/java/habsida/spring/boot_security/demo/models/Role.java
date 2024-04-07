package habsida.spring.boot_security.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(unique = true)
    private String role;




    @Override
    public String getAuthority() {
        return role;
    }
}
