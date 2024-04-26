package com.technicaltest.controllers;

import com.technicaltest.controllers.UserController;
import com.technicaltest.controllers.request.CreateUserDTO;
import com.technicaltest.models.UserEntity;
import com.technicaltest.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser_HappyPath() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername("testUser");
        createUserDTO.setPassword("testPassword");
        createUserDTO.setEmail("testEmail");

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .build();

        when(passwordEncoder.encode(any(String.class))).thenReturn("testPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        ResponseEntity<UserEntity> response = userController.createUser(createUserDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userEntity, response.getBody());
    }

    @Test
    public void createUser_NullUserDTO() {
        ResponseEntity<UserEntity> response = userController.createUser(null);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void createUser_EmptyUserDTO() {
        CreateUserDTO createUserDTO = new CreateUserDTO();

        ResponseEntity<UserEntity> response = userController.createUser(createUserDTO);

        assertEquals(400, response.getStatusCodeValue());
    }
}