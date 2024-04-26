package com.technicaltest.security.filters;

import com.technicaltest.security.jwt.JwtUtils;
import com.technicaltest.services.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImp userDetailsServiceImp;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && jwtUtils.isTokenValid(token)){

                String username = jwtUtils.getUserNameFromToken(token);

                UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        filterChain.doFilter(request, response);

    }

}
