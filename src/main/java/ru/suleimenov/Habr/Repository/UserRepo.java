package ru.suleimenov.Habr.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.suleimenov.Habr.entity.User;

import java.util.Optional;

/**
 * @author Neil Alishev
 */

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);

}
