package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    @Autowired
    private NotesMapper notesMapper;

    public List<Note> getAllNotes(Long userid) throws Exception {
        List<Note> notes = notesMapper.findByUserId(userid);
        if (notes == null) {
            throw new Exception();
        }
        return notes;
    }

    public void addNote(Note note, Long userid) {
        notesMapper.insertNote(note, userid);
    }

    public void updateNote(Note note, Long userId) {
        notesMapper.updateNote(note, userId);
    }

    public void deleteNote(int noteid, Long userId) {
        notesMapper.deleteNote(noteid, userId);
    }
}