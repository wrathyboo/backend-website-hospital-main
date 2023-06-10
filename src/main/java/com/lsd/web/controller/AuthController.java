package com.lsd.web.controller;

import com.lsd.web.exception.ErrorCode;
import com.lsd.web.exception.WebException;
import com.lsd.web.persistance.entity.Admin;
import com.lsd.web.persistance.mapper.AdminAdapter;
import com.lsd.web.service.UserService;
import com.lsd.web.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class AuthController {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/getToken")
    public ResponseEntity<?> login(@ModelAttribute Admin admin) {
        if(admin.getUsername() != null){
            return new ResponseEntity<>(jwtProvider.generateToken(userService.loadUserByUsername(admin.getUsername())), HttpStatus.OK);
        }
        throw new WebException(ErrorCode.UN_AUTHORIZED);
    }
}