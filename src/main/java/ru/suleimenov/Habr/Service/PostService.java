package ru.suleimenov.Habr.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.suleimenov.Habr.Repository.FavoritesRepo;
import ru.suleimenov.Habr.Repository.LikeRepo;
import ru.suleimenov.Habr.Repository.PostRepo;
import ru.suleimenov.Habr.Repository.UserRepo;
import ru.suleimenov.Habr.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FavoritesRepo favoritesRepo;

    @Autowired
    private TagService tagService;

    public List<Post> getAll(){
        return postRepo.findAllByNativeQuery();
    }
    @Transactional
    public Optional<Post> findById(Long id){
        Optional<Post> post = postRepo.findById(id);

            return post;
    }
    @Transactional
    public void save(Post post){
        postRepo.save(post);
    }
    @Transactional
    public boolean delete(Optional<Post> post){
        if (post.isPresent()){
            postRepo.delete(post.get());
            return true;

        }else return false;
    }
    @Transactional
    public void saveComment(Post post, Comment comment){


        post.addComment(comment);
        postRepo.save(post);

    }
    @Transactional
    public void addLike(Long post_id, User user){
        Optional<Post> post = findById(post_id);
        if (post.isPresent()){
            Optional<Like> like1 = likeRepo.findLikeByPostAndUser(post.get(),user);
            if (like1.isPresent()){
                post.get().addLike(true);
                likeRepo.delete(like1.get());
            }else {
                post.get().addLike(false);
                Like like = new Like();
                like.setUser(user);
                like.setPost(post.get());
                likeRepo.save(like);
            }
            postRepo.save(post.get());
        }else return;

    }

    @Transactional
    public void addFavorites(Long post_id, User user){
        Optional<Post> post = findById(post_id);
        if (post.isPresent()){
            Optional<Favorites> favorite = favoritesRepo.findFavoritesByPostAndUser(post.get(),user);
            if (favorite.isPresent()){
                post.get().addFavorites(true);
                favoritesRepo.delete(favorite.get());
            }else {
                post.get().addFavorites(false);
                Favorites favorites = new Favorites();
                favorites.setUser(user);
                favorites.setPost(post.get());
                favoritesRepo.save(favorites);
            }
            postRepo.save(post.get());
        }else return;

    }

    @Transactional
    public void addPost(Post post, User user){
        String text = post.getString();
        String[] words = text.split(",");
        List<Tag> tags = new ArrayList<>();
        for(String word : words){
            tagService.save(new Tag(word.trim()));
            tags.add(tagService.findByNameAndGetTag(word.trim()));
        }
        post.setTag(tags);
        post.setUser(user);
        post.setDate(LocalDate.now());
        post.setTime(LocalTime.now());
        save(post);
    }

    @Transactional
    public void updatePost(Post post, User user){
        String text = post.getString();
        String[] words = text.split(",");
        List<Tag> tags = new ArrayList<>();
        for(String word : words){
            tagService.save(new Tag(word.trim()));
            tags.add(tagService.findByNameAndGetTag(word.trim()));
        }
        post.setUser(user);
        post.setTag(tags);
        post.setDate(LocalDate.now());
        post.setTime(LocalTime.now());
        save(post);
    }

    public List<Post> getAllOrderByTag(Long id){

        return postRepo.findAllByTagOrderByLikeCount(id);
    }




}
