package com.user.auth.controllers;

import com.user.auth.model.AuthRequest;
import com.user.auth.model.RefreshRequest;
import com.user.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        return authenticationService.login(authRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> generateViaRefreshToken(@RequestBody RefreshRequest refreshRequest){
        return authenticationService.refresh(refreshRequest);
    }
}
