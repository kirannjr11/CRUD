package habsida.spring.boot_security.demo.repositories;

import habsida.spring.boot_security.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    default Optional<User> findByName(String userName) {
        return findAll().stream().filter(user -> user.getFirstName().equals(userName)).findFirst();
    };
}
