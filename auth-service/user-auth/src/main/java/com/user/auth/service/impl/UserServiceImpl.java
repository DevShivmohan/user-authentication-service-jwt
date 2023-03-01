package com.user.auth.service.impl;

import com.user.auth.entity.User;
import com.user.auth.exception.GenericException;
import com.user.auth.repository.UserRepository;
import com.user.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<?> addUser(User user) {
        user.setId(0);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreatedTime(new Date());
        var responseUser=userRepository.save(user);
        if(responseUser==null)
            throw new GenericException("User does not create",HttpStatus.REQUEST_TIMEOUT.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @Override
    public ResponseEntity<?> updateUser(User user, long id) {
        var userFromDB= userRepository.findById(id);
        if(userFromDB==null)
            throw new GenericException("User not found via id "+id,HttpStatus.NOT_FOUND.value());
        user.setId(id);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreatedTime(new Date());
        var responseUser=userRepository.save(user);
        if(responseUser==null)
            throw new GenericException("User does not create",HttpStatus.REQUEST_TIMEOUT.value());
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @Override
    public ResponseEntity<?> deleteUser(long id) {
        var user=userRepository.findById(id).
                orElseThrow(()->new GenericException("User does not exists",HttpStatus.NOT_FOUND.value()));
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Override
    public ResponseEntity<?> getUser(long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(id).orElseThrow(()->new GenericException("User does not exists",HttpStatus.NOT_FOUND.value())));
    }

    @Override
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userRepository.findAll());
    }
}
