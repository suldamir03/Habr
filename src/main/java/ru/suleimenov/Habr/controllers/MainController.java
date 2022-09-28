package ru.suleimenov.Habr.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.suleimenov.Habr.Repository.TagRepo;
import ru.suleimenov.Habr.Repository.UserRepo;
import ru.suleimenov.Habr.Service.CommentService;
import ru.suleimenov.Habr.Service.PostService;
import ru.suleimenov.Habr.Service.TagService;
import ru.suleimenov.Habr.Service.UserService;
import ru.suleimenov.Habr.entity.Comment;
import ru.suleimenov.Habr.entity.Post;
import ru.suleimenov.Habr.entity.Tag;
import ru.suleimenov.Habr.entity.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Logger;
@Slf4j
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
    /*@PostMapping("posts/{id}/like")
    public String liking(@PathVariable("id") Long id,@AuthenticationPrincipal User user){
        postService.addLike(id,user.getId());
        return "redirect:/home";
    }*/

    /*@PostMapping("comments/{id}/like")
    public String likeComment(@PathVariable("id") Long id,@AuthenticationPrincipal User user){
        commentService.addLike(id,user.getId());
        return "redirect:/home";
    }*/

    @PostMapping("/posts/{id}/comments/new")
    public String commentNew(@ModelAttribute("comm") Comment comment,@PathVariable("id") Long post_id,@AuthenticationPrincipal User user){

        //TODO: do this fucking comments
//        Comment comment1 = commentService.findByAll(postService.findById(post_id).orElse(null), user,comment.getDate(),comment.getTime());
        postService.saveComment(postService.findById(post_id).orElse(null),commentService.saveComment(comment,user,post_id));
        return "post";
    }

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

    @GetMapping("posts/add")
    public String newPost(Model model, @AuthenticationPrincipal User user) {
        return "redirect:/home";
    }

    @PostMapping("/posts/add")
    public String create(@ModelAttribute("addpost") @Valid Post post,
                         BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors())
            return "redirect:/home";
        String text = post.getString();
        String[] words = text.split(",");
        List<Tag> tags = new ArrayList<>();
        for(String word : words){
            tagService.save(new Tag(word));
            tags.add(tagService.findByNameAndGetTag(word));
        }
        post.setTag(tags);
        post.setUser(user);
        post.setDate(LocalDate.now());
        post.setTime(LocalTime.now());
        postService.save(post);
        return "redirect:/home";
    }



    @GetMapping("posts/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("person", postService.findById(id));
        return "habr/edit";
    }

    @PatchMapping("posts/{id}/edit")
    public String update(@ModelAttribute("post") @Valid Post post, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "redirect:/{id}/edit";

        postService.save(post);
        return "redirect:/profile";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {

        if (postService.delete(postService.findById(id)) == true){
            return "redirect:/home";
        }
        return "redirect:/home";
    }

    @GetMapping("/users/{id}")
    public String userProfile(Model model,@PathVariable("id") Long id){
        model.addAttribute("user", userService.findById(id));
        return "profile";
    }
}
