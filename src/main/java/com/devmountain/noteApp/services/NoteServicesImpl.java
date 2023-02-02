package com.devmountain.noteApp.services;

import com.devmountain.noteApp.dtos.NoteDto;
import com.devmountain.noteApp.entites.Note;
import com.devmountain.noteApp.entites.User;
import com.devmountain.noteApp.respositories.NoteRespository;
import com.devmountain.noteApp.respositories.UserRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteServicesImpl implements NoteServices {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private NoteRespository noteRespository;

    @Override
    @Transactional
    public void addNote(NoteDto noteDto, Long userId) {
        Optional<User> userOptional = userRespository.findById(userId);
        Note note = new Note(noteDto);
        userOptional.ifPresent(note::setUser);
        noteRespository.saveAndFlush(note);
    }

    @Override
    @Transactional
    public void deleteNoteById(Long noteId) {
        Optional<Note> noteOptional = noteRespository.findById(noteId);
        noteOptional.ifPresent(note -> noteRespository.delete(note));
    }

    @Override
    @Transactional
    public void updateNoteById(NoteDto noteDto) {
        Optional<Note> noteOptional = noteRespository.findById(noteDto.getId());
        noteOptional.ifPresent(note -> {
            note.setBody(noteDto.getBody());
            noteRespository.saveAndFlush(note);
        });

    }

    @Override
    public List<NoteDto> getAllNotesByUserId(Long userId) {
        Optional<User> userOptional = userRespository.findById(userId);
        if (userOptional.isPresent()) {
            List<Note> noteList = noteRespository.findAllByUserEquals(userOptional.get());
            return noteList.stream().map(note -> new NoteDto(note)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<NoteDto> getNoteById(Long noteId) {
        Optional<Note> noteOptional = noteRespository.findById(noteId);
        if (noteOptional.isPresent()) {
            return Optional.of(new NoteDto(noteOptional.get()));
        }
        return Optional.empty();
    }

}
