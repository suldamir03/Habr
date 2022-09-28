package ru.suleimenov.Habr.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.suleimenov.Habr.Repository.PostRepo;
import ru.suleimenov.Habr.Repository.UserRepo;
import ru.suleimenov.Habr.entity.Comment;
import ru.suleimenov.Habr.entity.Post;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    public List<Post> getAll(){
        return postRepo.findAll();
    }

    public Optional<Post> findById(Long id){
        Optional<Post> post = postRepo.findById(id);

            return post;
    }
  //TODO:  @Transactional
    public void save(Post post){
        postRepo.save(post);
    }

    public boolean delete(Optional<Post> post){
        if (post.isPresent()){
            postRepo.delete(post.get());
            return true;

        }else return false;
    }

    public void saveComment(Post post, Comment comment){


        post.addComment(comment);
        postRepo.save(post);

    }





}
