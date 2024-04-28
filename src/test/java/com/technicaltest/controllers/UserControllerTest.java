package com.technicaltest.controllers;

import com.technicaltest.controllers.request.UserDTO;
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
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("testPassword");
        userDTO.setEmail("testEmail");

        UserEntity userEntity = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build();

        when(passwordEncoder.encode(any(String.class))).thenReturn("testPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        ResponseEntity<UserEntity> response = userController.createUser(userDTO);

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
        UserDTO userDTO = new UserDTO();

        ResponseEntity<UserEntity> response = userController.createUser(userDTO);

        assertEquals(400, response.getStatusCodeValue());
    }
}