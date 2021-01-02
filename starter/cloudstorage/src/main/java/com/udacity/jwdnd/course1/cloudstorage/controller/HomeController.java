package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Usuario;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final NotesService noteService;
    private final FileService fileService;
    private final AuthService AuthService;
    private final UserService userService;

    @Autowired
    public HomeController(NotesService noteService, FileService fileService, AuthService AuthService, UserService userService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.AuthService = AuthService;
        this.userService = userService;
    }

    @GetMapping({"/", "/home", "/dashboard"})
    public ModelAndView GetHome(Authentication authentication) throws Exception{
        Usuario user = userService.loadUserByUsername(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("notes", this.noteService.getAllNotes(user.getUserId()));
        modelAndView.addObject("decrypt", this.AuthService);
        modelAndView.addObject("credentials", this.AuthService.getAllCredential(user.getUserId()));
        modelAndView.addObject("files", this.fileService.getAllFilesByUserId(user.getUserId()));
        return modelAndView;
    }


}
