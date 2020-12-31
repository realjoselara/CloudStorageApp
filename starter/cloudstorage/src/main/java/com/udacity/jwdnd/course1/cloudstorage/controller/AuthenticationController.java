package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthenticationController {


    @Autowired
    private AuthService AuthService;
    private UserService userService;

    @GetMapping(value = "/signup")
    public String signUp(Model model, @RequestParam("error") Optional<String> error, @RequestParam("errorType")Optional<String> errorType) {
        if (error.isPresent() && errorType.isPresent()) {
            model.addAttribute("error", true);

            if (errorType.get().equals("generic")) {
                model.addAttribute("errorMessage", "There was an error during the user creation. Try again later or contact support.");
            } else {
                model.addAttribute("errorMessage", "The username already exists. Try with a different one.");
            }
        }

        return "signup";
    }
}
