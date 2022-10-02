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
import ru.suleimenov.Habr.entity.UserConfiguration;


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

    //USER VIEW PAGE
    @GetMapping("/users/{id}")
    public String userProfile(Model model,@PathVariable("id") Long id, @AuthenticationPrincipal User user){
        if (user.getId() == id){
            model.addAttribute("user", userService.findById(id));
            return "myProfile";
        }
        model.addAttribute("user", userService.findById(id));
        return "profile";
    }

    // USER privacy configuration
    @GetMapping("/users/{id}/configure")
    public String confAcc(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user){
        if (id == user.getId()){
            //TODO: do it
            model.addAttribute("user" ,user);

            model.addAttribute("conf", user.getUserConfiguration());

            return "conf";
        } else return "redirect:/users/{id}";
    }

    @PostMapping("/users/{id}/configure")
    public String confAcc(@PathVariable("id") Long id, @AuthenticationPrincipal User user_conn, @ModelAttribute("conf")UserConfiguration userConfiguration) {
        if (id == user_conn.getId()){
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"  +userConfiguration.isFavHiding() + userConfiguration.isNameHiding());
            user_conn.setUserConfiguration(userConfiguration);
            userService.save(user_conn);
            userService.saveUserConfig(userConfiguration);
            return "redirect:/users/{id}";
        }

        return "redirect:/home";
    }

    //USER UPDATE INFO

    @GetMapping("/users/{id}/update")
    public String updateUserInfo(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user){
        if (id == user.getId()){
            //TODO: do it
            model.addAttribute("user" ,user);

            return "update";
        } else return "redirect:/users/{id}";
    }
    @PostMapping("/users/{id}/update")
    public String updateUserInfo(@PathVariable("id") Long id, @AuthenticationPrincipal User user_conn, @ModelAttribute("user")User user_model){
        if (id != user_conn.getId()){
            return "redirect:/home";

        }
        /*userConfiguration.setUser(user);
        user.setUserConfiguration(userConfiguration);*/
        userService.save(user_model);
        return "redirect:/users/{id}";
    }

    //
}
