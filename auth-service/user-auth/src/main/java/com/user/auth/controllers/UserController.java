package com.user.auth.controllers;

import com.user.auth.entity.User;
import com.user.auth.model.UserReqDTO;
import com.user.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserReqDTO userReqDTO){
        return userService.addUser(User.builder().name(userReqDTO.getName()).username(userReqDTO.getUsername())
                        .password(userReqDTO.getPassword())
                .build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user,@PathVariable("id") long id){
        return userService.updateUser(user,id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id){
        return userService.deleteUser(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> fetchUser(@PathVariable("id") long id){
        return userService.getUser(id);
    }

    @GetMapping
    public ResponseEntity<?> fetchUsers(){
        return userService.getUsers();
    }
}
