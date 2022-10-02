package ru.suleimenov.Habr.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.suleimenov.Habr.Repository.PostRepo;
import ru.suleimenov.Habr.Service.CommentService;
import ru.suleimenov.Habr.Service.PostService;
import ru.suleimenov.Habr.Service.TagService;
import ru.suleimenov.Habr.Service.UserService;
import ru.suleimenov.Habr.entity.Comment;
import ru.suleimenov.Habr.entity.Post;
import ru.suleimenov.Habr.entity.User;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    //LIKING
    @PostMapping("posts/{id}/like")
    public String liking(@PathVariable("id") Long id,@AuthenticationPrincipal User user){
        postService.addLike(id,user);
        return "redirect:/home";
    }


    //FAVORITES
    @PostMapping("posts/{id}/fav")
    public String favor(@PathVariable("id") Long id,@AuthenticationPrincipal User user){
        postService.addFavorites(id,user);
        return "redirect:/home";
    }


    //ADDING OF COMMENT
    @PostMapping("/posts/{id}/comments/new")
    public String commentNew(@ModelAttribute("comm") Comment comment,@PathVariable("id") Long post_id,@AuthenticationPrincipal User user){

        postService.saveComment(postService.findById(post_id).orElse(null),commentService.saveComment(comment,user,post_id));
        return "post";
    }

    //POST VIEW
    @GetMapping("posts/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()){
            model.addAttribute("post", post.get());
            model.addAttribute("comm", new Comment());
            return "post";

        }else //TODO: create error page
            return "error";

    }

    // NEW POST
    @GetMapping("posts/add")
    public String newPost(Model model, @AuthenticationPrincipal User user) {
        return "redirect:/home";
    }

    @PostMapping("/posts/add")
    public String create(@ModelAttribute("addpost") @Valid Post post,
                         BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors())
            return "redirect:/home";

        postService.addPost(post, user);
        return "redirect:/home";
    }



    //POST EDIT
    //TODO: DO IT there unknow error
    @GetMapping("/posts/{id}/edit")
    public String postEdit(Model model, @PathVariable("id") Long id, @AuthenticationPrincipal User user){
        Optional<Post> post = postService.findById(id);

        if (user.getId() == post.get().getUserId()){
            post.get().setUser(user);
            model.addAttribute("post", post.get());
            return "editPost";
        }else {
            return "redirect:/posts/{id}";

        }

    }

    @PostMapping("/posts/{id}/edit")
    public String postEdit(@ModelAttribute("post") Post post, @ModelAttribute("user") User user){
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAa user.getId()" + user.getId());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAa post.get().getUserId()" + post.getUserId());
        /*if (user.getId() != post.getUserId()){
            return "redirect:/posts/{id}";
        }*/
        postService.updatePost(post, user);
        return "redirect:/post/{id}";
    }

    //

}
