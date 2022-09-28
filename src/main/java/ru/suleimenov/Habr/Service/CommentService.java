package ru.suleimenov.Habr.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.suleimenov.Habr.Repository.CommentRepo;
import ru.suleimenov.Habr.Repository.PostRepo;
import ru.suleimenov.Habr.Repository.UserRepo;
import ru.suleimenov.Habr.entity.Comment;
import ru.suleimenov.Habr.entity.Post;
import ru.suleimenov.Habr.entity.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;

    public void save(Comment comment){
        commentRepo.save(comment);
    }

    public Comment findById(Long id){

        return commentRepo.findById(id).get();
    }
    public Comment findByAll(Post post, User user, LocalDate localDate, LocalTime localTime){
        Optional<Comment> comment = commentRepo.findCommentByPostAndUserAndDateAndTime(post,user, localDate, localTime);
        if (comment.isPresent()){
            return comment.get();
        }else return null;

    }

    public Comment saveComment(Comment comment, User user, Long post_id){
        Post post1 = postRepo.findById(post_id).orElse(null);
        comment.setUser(user);
        comment.setPost(post1);
        comment.setDate(LocalDate.now());
        comment.setTime(LocalTime.now());
        save(comment);
        return findByAll(post1,user,comment.getDate(), comment.getTime());
    }




}
