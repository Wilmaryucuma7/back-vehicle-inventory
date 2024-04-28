package com.technicaltest.controllers;

import com.technicaltest.controllers.request.UserDTO;
import com.technicaltest.models.UserEntity;
import com.technicaltest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRespository;

    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDTO userDTO){

        UserEntity userEntity = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .build();
        userRespository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

}
