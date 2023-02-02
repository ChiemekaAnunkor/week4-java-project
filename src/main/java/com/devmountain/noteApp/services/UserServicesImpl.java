package com.devmountain.noteApp.services;

import com.devmountain.noteApp.dtos.UserDto;
import com.devmountain.noteApp.entites.User;
import com.devmountain.noteApp.respositories.UserRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {


    @Autowired
    private UserRespository userRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<String> addUser(UserDto userDto) {

        List<String> response = new ArrayList<>();

        User user = new User(userDto);
        userRespository.saveAndFlush(user);
        response.add("http://localhost:8080/login.html");
        return response;
    }

    @Override
    public List<String> userLogin(UserDto userDto) {
        List<String> response = new ArrayList<>();
        Optional<User> userOptional = userRespository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            if (passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())) {
                response.add("http://localhost:8080/home.html");
                response.add(String.valueOf(userOptional.get().getId()));
            } else {
                response.add("username or password incorrect");
            }
        } else {
            response.add("username or password incorrect");
        }

        return response;
    }


}
