package com.technicaltest.security.filters;

import com.technicaltest.models.UserEntity;
import com.technicaltest.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserEntity user = null;
        String userName = "";
        String userPassword = "";
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            userName = user.getUsername();
            userPassword = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, userPassword);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String token = jwtUtils.generateAccessToken(user.getUsername());

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("userName", user.getUsername());
        httpResponse.put("error", false);

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
