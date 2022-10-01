package ru.suleimenov.Habr.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.suleimenov.Habr.entity.Like;
import ru.suleimenov.Habr.entity.Post;
import ru.suleimenov.Habr.entity.User;

import java.util.Optional;

@Repository
public interface LikeRepo extends CrudRepository<Like,Long> {
    Optional<Like> findLikeByPostAndUser(Post post, User user);
}
