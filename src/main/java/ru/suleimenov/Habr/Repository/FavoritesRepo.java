package ru.suleimenov.Habr.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.suleimenov.Habr.entity.Favorites;
import ru.suleimenov.Habr.entity.Post;
import ru.suleimenov.Habr.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritesRepo extends CrudRepository<Favorites, Long> {


    Optional<Favorites> findFavoritesByPostAndUser(Post post, User user);
}
