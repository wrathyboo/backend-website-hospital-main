package com.lsd.web.controller;

import com.lsd.web.exception.ErrorCode;
import com.lsd.web.exception.WebException;
import com.lsd.web.persistance.model.AdminModel;
import com.lsd.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("")
    public List<AdminModel> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK).getBody();
    }
    @GetMapping(value = "/{id}")
    public AdminModel findById(@PathVariable("id") String id){
        AdminModel a = userService.findById(id);
        if(a == null){
            throw new WebException(ErrorCode.NOT_FOUND);
        }
        return new ResponseEntity<>(a, HttpStatus.OK).getBody();
    }
    @PostMapping(value = "/add")
    public ResponseEntity<AdminModel> add(@ModelAttribute AdminModel adminModel) {
        adminModel.setPassword(encoder.encode(adminModel.getPassword()));
        AdminModel adminModel1 = userService.add(adminModel);

        return new ResponseEntity<>(adminModel1, HttpStatus.CREATED);
    }
    @PostMapping(value = "/edit")
    public ResponseEntity<AdminModel> edit(@ModelAttribute AdminModel adminModel) {
        adminModel.setPassword(encoder.encode(adminModel.getPassword()));
        AdminModel adminModel1 = userService.edit(adminModel);
        return new ResponseEntity<>(adminModel1, HttpStatus.CREATED);
    }
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<AdminModel> delete(@PathVariable("id") String id) {
        if(userService.remove(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            throw new WebException(ErrorCode.INTERNAL_SERVER);
        }
    }
}
