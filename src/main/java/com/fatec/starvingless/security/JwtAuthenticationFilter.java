package com.fatec.starvingless.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String AUTH_URL = "/login";

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        // api/authenticate
        setFilterProcessesUrl(AUTH_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {

        try {
            JwtLoginInput login = new ObjectMapper().readValue(request.getInputStream(), JwtLoginInput.class);
            String email = login.getEmail();
            String password = login.getPassword();

            if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
                throw new BadCredentialsException("Invalid username/password.");
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(email, password);

            return authenticationManager.authenticate(auth);
        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        String jwtToken = JwtUtil.createToken(user);

//        String json = ServletUtil.getJson("token", jwtToken);
        String json = UserDTO.create(user, jwtToken).toJson();
        ServletUtil.write(response, HttpStatus.OK, json);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException error) throws IOException, ServletException {

        String json = ServletUtil.getJson("error", "Incorrect login");
        ServletUtil.write(response, HttpStatus.UNAUTHORIZED, json);
    }


}

