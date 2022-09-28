package ru.suleimenov.Habr.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.suleimenov.Habr.entity.Comment;
import ru.suleimenov.Habr.entity.Post;
import ru.suleimenov.Habr.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface CommentRepo extends CrudRepository<Comment,Long> {
    @Override
    Optional<Comment> findById(Long id);
    Optional<Comment> findCommentByPostAndUserAndDateAndTime(Post post, User user, LocalDate localDate, LocalTime localTime);
}
