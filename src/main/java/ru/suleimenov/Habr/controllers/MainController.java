package ru.suleimenov.Habr.controllers;

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
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;



    @GetMapping()
    public String noWord() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("posts", postService.getAll());
        model.addAttribute("comm", new Comment());
        model.addAttribute("addpost", new Post());
        model.addAttribute("user", user);

        return "home";
    }


    /*@PostMapping("comments/{id}/like")
    public String likeComment(@PathVariable("id") Long id,@AuthenticationPrincipal User user){
        commentService.addLike(id,user.getId());
        return "redirect:/home";
    }*/




    //HOME VIEW ORDERED BY TAG
    @GetMapping("/posts/orderByTag/{id}")
    public String postsByTag(Model model,@PathVariable("id") Long id){
        model.addAttribute("tag", tagService.findTagById(id));
        model.addAttribute("posts", postService.getAllOrderByTag(id));
        return "orderByTag";
    }




}
