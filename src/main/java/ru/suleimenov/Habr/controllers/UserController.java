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


@Controller
public class UserController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;


    @GetMapping("/users/{id}")
    public String userProfile(Model model,@PathVariable("id") Long id){
        model.addAttribute("user", userService.findById(id));
        return "profile";
    }

    @GetMapping("/users/{id}/configure")
    public String confAcc(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user){
        if (id == user.getId()){
            model.addAttribute("user" ,user);

            return "conf";
        } else return "redirect:/users/{id}";
    }

    @PostMapping("/users/{id}/update")
    public String updateUserInfo(@PathVariable("id") Long id, @AuthenticationPrincipal User user, @ModelAttribute("user") User user1){
        if (id != user.getId()){
            return "redirect:/users/{id}";

        }
        userService.save(user1);
        return "redirect:/users/{id}";
    }
}
