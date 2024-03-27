package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.model.LoginRequest;
import com.example.springsecuritydemo.model.LoginResponse;
import com.example.springsecuritydemo.security.AuthUserDetail;
import com.example.springsecuritydemo.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtProvider jwtProvider;

    @PostMapping("auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Provided credential is invalid.");
        }

        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();

        String token = jwtProvider.createToken(authUserDetail);

        return LoginResponse.builder()
                .message("Welcome " + authUserDetail.getUsername())
                .token(token)
                .build();
    }
}
