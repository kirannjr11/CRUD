package habsida.spring.boot_security.demo.services;

import habsida.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> listRoles();

    void add(Role role);

    Optional<Role> findById(Long id);

}
