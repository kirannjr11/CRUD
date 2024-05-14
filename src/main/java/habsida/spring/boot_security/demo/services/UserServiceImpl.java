package habsida.spring.boot_security.demo.services;

import habsida.spring.boot_security.demo.models.Role;
import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.repositories.RoleRepository;
import habsida.spring.boot_security.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = user.getRoles();
        if(roles != null && !roles.isEmpty()) {
            for(Role role : roles) {
                if(role.getId() == null) {
                    roleRepository.save(role);
                }
            }
        }
        userRepository.save(user);
        return user;
    }
    @Override
    @Transactional
    public User update(Long id, User user) {
        String newPassword = user.getPassword();
        if (newPassword != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        Set<Role> roles = user.getRoles();
        if(roles != null && !roles.isEmpty()) {
            for(Role role : roles) {
                if(role.getId() == null) {
                    roleRepository.save(role);
                }
            }
        }
        userRepository.save(user);
        return user;
    }


    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void remove(long id) {
        userRepository.deleteById(id);
    }





    @Override
    public Optional<User> userById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByName(String userName) {
        return userRepository.findByName(userName);
    }
}
