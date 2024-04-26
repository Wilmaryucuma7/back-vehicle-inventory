package com.technicaltest.controllers;

import com.technicaltest.controllers.request.CreateUserDTO;
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
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .build();
        userRespository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

}
