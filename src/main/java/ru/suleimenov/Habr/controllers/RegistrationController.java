package ru.suleimenov.Habr.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.suleimenov.Habr.Service.UserService;
import ru.suleimenov.Habr.entity.User;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/reg")
    public String reg(Model model){
        model.addAttribute("user", new User());
        return "reg";
    }
    @PostMapping("/registration")
    public String reg(@ModelAttribute("user") User user){
        userService.registerDefaultUser(user);
        return "redirect:/login";
    }
    /*private UserService userService;
    @GetMapping("/reg")
    public String reg(Model model){
        model.addAttribute("user", new User());
        return "habr/reg";
    }
    @PostMapping
    public String regUser(@ModelAttribute("user") User user){
        if (userService.loadUserByUsername(user.getUsername()) != null){
            return "habr/auth";
        }

        if (userService.registerDefaultUser(user) == true){
            return "habr/auth";
        }else return "habr/reg";
    }*/
}

