package habsida.spring.boot_security.demo.services;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{


    private RoleRepository roleRepository;

    @Override
    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Optional<Role> findById(byte id) {
        return roleRepository.findById(id);
    }

}
