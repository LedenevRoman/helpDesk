package com.training.rledenev.controllers;

import com.training.rledenev.dto.UserDto;
import com.training.rledenev.enums.Role;
import com.training.rledenev.model.User;
import com.training.rledenev.security.Token;
import com.training.rledenev.security.jwt.JwtProvider;
import com.training.rledenev.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping
    public ResponseEntity<Token> auth(@Valid @RequestBody UserDto userDto) {
        User user = userService.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        String tokenString = jwtProvider.generateToken(user.getEmail());
        Token tokenEntity = new Token();
        tokenEntity.setToken(tokenString);
        return ResponseEntity.ok().body(tokenEntity);
    }

    @GetMapping
    public Role auth() {
        return userService.getAuthorizedUserRole();
    }
}
