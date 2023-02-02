package com.devmountain.noteApp.respositories;

import com.devmountain.noteApp.entites.Note;
import com.devmountain.noteApp.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRespository extends JpaRepository<Note, Long> {


    List<Note> findAllByUserEquals(User user);

}
