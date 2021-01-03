package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Usuario;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute("SpringWeb") Usuario user, Model model) {
        String errorMessage;

        if (user == null) {
            return "redirect:signup";
        }

        try {
            userService.register(user);
            model.addAttribute("signupSuccess", true);
            return "redirect:login?success";

        } catch (Exception e) {
            errorMessage = "There was an error signing you up. Please try again.";
            model.addAttribute("signupError", errorMessage);
            return "redirect:signup?error";
        }
    }
}