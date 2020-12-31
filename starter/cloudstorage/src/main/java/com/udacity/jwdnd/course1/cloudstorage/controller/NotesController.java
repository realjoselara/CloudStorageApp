package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.Usuario;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {

    private final UserService userService;
    private NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService, UserService userService){
        this.notesService = notesService;
        this.userService = userService;
    }

    @PostMapping("/note")
    public String createOrUpdateNote(Authentication authentication, Note note) {
        Usuario user = userService.loadUserByUsername(authentication.getName());

        if (note.getNoteId() != null) {
            notesService.updateNote(note, user.getUserId());
        } else {
            notesService.addNote(note, user.getUserId());
        }
        return "redirect:/result?success";
    }

    @GetMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable("id") int id, Authentication authentication) {
        Usuario user = userService.loadUserByUsername(authentication.getName());
        if (id > 0) {
            notesService.deleteNote(id, user.getUserId());
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}