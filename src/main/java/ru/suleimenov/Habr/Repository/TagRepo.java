package ru.suleimenov.Habr.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.suleimenov.Habr.entity.Tag;

import java.util.Optional;

@Repository
public interface TagRepo extends CrudRepository<Tag, Long> {
    Optional<Tag> findTagByName(String name);
}
