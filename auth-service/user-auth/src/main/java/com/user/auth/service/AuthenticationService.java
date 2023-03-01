package com.user.auth.service;

import com.user.auth.model.AuthRequest;
import com.user.auth.model.RefreshRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> login(AuthRequest authRequest);
    ResponseEntity<?> refresh(RefreshRequest refreshRequest);
}
