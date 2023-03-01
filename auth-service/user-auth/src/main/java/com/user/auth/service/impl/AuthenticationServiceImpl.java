package com.user.auth.service.impl;

import com.user.auth.config.CustomUserDetailsService;
import com.user.auth.exception.GenericException;
import com.user.auth.model.AuthRequest;
import com.user.auth.model.AuthResponse;
import com.user.auth.model.RefreshRequest;
import com.user.auth.repository.UserRepository;
import com.user.auth.service.AuthenticationService;
import com.user.auth.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public ResponseEntity<?> login(AuthRequest authRequest) {
        if(authRequest.getUsername()==null || authRequest.getPassword()==null)
            throw new GenericException("Bad credential", HttpStatus.BAD_REQUEST.value());
        var user=userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(()->new GenericException("Bad credential",HttpStatus.BAD_REQUEST.value()));
        if(!bCryptPasswordEncoder.matches(authRequest.getPassword(), user.getPassword()))
            throw new GenericException("Bad credential", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.OK).body(AuthResponse.builder().accessToken(jwtUtil.generateAccessToken(authRequest.getUsername()))
                .refreshToken(jwtUtil.generateRefreshToken(authRequest.getUsername())).build());
    }

    @Override
    public ResponseEntity<?> refresh(RefreshRequest refreshRequest) {
        if(refreshRequest.getRefreshToken()==null || refreshRequest.getRefreshToken().isBlank())
            throw new GenericException("Invalid refresh token",HttpStatus.BAD_REQUEST.value());
        var userDetails=customUserDetailsService.loadUserByUsername(jwtUtil.extractUsername(refreshRequest.getRefreshToken()));
        if(!jwtUtil.validateIsRefreshToken(refreshRequest.getRefreshToken(),userDetails))
            throw new GenericException("Incorrect refresh token",HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.OK).body(AuthResponse.builder().accessToken(jwtUtil.generateAccessToken(jwtUtil.extractUsername(refreshRequest.getRefreshToken())))
                .refreshToken(jwtUtil.generateRefreshToken(jwtUtil.extractUsername(refreshRequest.getRefreshToken()))).build());
    }
}
