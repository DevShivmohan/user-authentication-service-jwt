package com.user.auth.service;

import com.user.auth.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> addUser(User user);
    ResponseEntity<?> updateUser(User user,long id);
    ResponseEntity<?> deleteUser(long id);
    ResponseEntity<?> getUser(long id);

    ResponseEntity<?> getUsers();
}
