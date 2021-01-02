package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Usuario;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialsController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public CredentialsController(AuthService authService, UserService userService){
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/credential")
    public String saveOrUpdateCredentials(Authentication authentication, Credential credential) {
        Usuario user = userService.loadUserByUsername(authentication.getName());
        Credential tempCredential = authService.getCredentialByUsername(credential.getUsername());
        if (credential.getCredentialId() != null || tempCredential != null) {
            authService.updateCredential(credential, user.getUserId());
        }
        else {
            authService.addCredential(credential, user.getUserId());
        }
        return "redirect:/result?success";
    }

    @GetMapping("/credential/delete/{id}")
    public String deleteCredentials(@PathVariable("id") Long id, Authentication authentication) {
        Usuario user = userService.loadUserByUsername(authentication.getName());

        if (id > 0) {
            authService.deleteCredential(id, user.getUserId());
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}
