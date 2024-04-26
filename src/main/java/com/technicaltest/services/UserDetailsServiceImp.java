package com.technicaltest.services;

import com.technicaltest.models.UserEntity;
import com.technicaltest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserDetailsServiceImp implements UserDetailsService {
    public static final String USUARIO_O_CONTRASENA_INCORRECTOS = "Usuario o contraseña incorrectos";

    @Autowired
    private UserRepository userRespository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRespository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USUARIO_O_CONTRASENA_INCORRECTOS));

        return new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                Collections.emptyList()
        );
    }
}
