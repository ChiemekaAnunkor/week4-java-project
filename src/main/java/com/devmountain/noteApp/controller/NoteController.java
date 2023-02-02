package com.devmountain.noteApp.controller;

import com.devmountain.noteApp.dtos.NoteDto;
import com.devmountain.noteApp.services.NoteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {
    @Autowired
    private NoteServices noteServices;

    @GetMapping("/user/{userId}")
    public List<NoteDto> getNotesByUser(@PathVariable Long userId) {
        return noteServices.getAllNotesByUserId(userId);
    }

    @PostMapping("/user/{userId}")
    public void addNote(@RequestBody NoteDto noteDto, @PathVariable Long userId) {
        noteServices.addNote(noteDto, userId);
    }


    @DeleteMapping("/{noteId}")
    public void deleteNoteById(@PathVariable Long noteId) {
        noteServices.deleteNoteById(noteId);
    }

    @PutMapping
    public void updateNote(@RequestBody NoteDto noteDto) {
        noteServices.updateNoteById(noteDto);
    }

    @GetMapping("/{noteId}")
    public Optional<NoteDto> getNoteById(@PathVariable Long noteId) {
        return noteServices.getNoteById(noteId);
    }

}
