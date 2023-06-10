package com.lsd.web.controller;

import com.lsd.web.persistance.model.AdminModel;
import com.lsd.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin
public class ClientController {
    @Autowired
    UserService userService;
    @GetMapping("/users")
    public List<AdminModel> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK).getBody();
    }
}
