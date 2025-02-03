package com.walletApp.wallet_application.com.jwtAuthentication.controller;

import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.AuthenticationRequest;
import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.AuthenticationResponse;
import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.RegisterRequest;
import com.walletApp.wallet_application.com.jwtAuthentication.User;
import com.walletApp.wallet_application.com.jwtAuthentication.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
// exposed RestfulApi for login and Register
public class AuthenticationController {
    private final AuthService service;
    private String jwtToken;


    @GetMapping("/username")
    public String getUsername(@NotNull @RequestHeader("Authorization") String token) {
        jwtToken = token.replace("Bearer ", "");
       User user =service.getUserDetails(jwtToken);
    return user.getName();
    }

//    registering a new user http://localhost:8080/netpipo/api/auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.ok(service.register(request));
    }

    //    registering a new user http://localhost:8080/netpipo/api/auth/authenticate
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {

        return ResponseEntity.ok(service.authenticate(request));
    }
}
