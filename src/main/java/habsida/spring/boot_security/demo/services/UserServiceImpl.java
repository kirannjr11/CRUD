package habsida.spring.boot_security.demo.services;

import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public void remove(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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
