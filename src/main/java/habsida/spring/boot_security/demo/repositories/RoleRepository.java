package habsida.spring.boot_security.demo.repositories;

import habsida.spring.boot_security.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Byte> {
}
