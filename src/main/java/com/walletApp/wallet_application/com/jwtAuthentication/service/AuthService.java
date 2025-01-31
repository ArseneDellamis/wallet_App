package com.walletApp.wallet_application.com.jwtAuthentication.service;


import com.walletApp.wallet_application.com.jwtAuthentication.AuthenticationConfig.JwtService;
import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.AuthenticationRequest;
import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.AuthenticationResponse;
import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.RegisterRequest;
import com.walletApp.wallet_application.com.jwtAuthentication.Role;
import com.walletApp.wallet_application.com.jwtAuthentication.User;
import com.walletApp.wallet_application.com.jwtAuthentication.dao.RoleRepository;
import com.walletApp.wallet_application.com.jwtAuthentication.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    /*
    this class is responsible for generating token
    when a user is register and when authenticated
    it used Authentication Manager component to get
    to generate a token out of emailAndPassword
     */
    /*
    also uses JwtService class this has Method definition
    generating token, getClaims, etc...
     */

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService service;
    private final AuthenticationManager manager;


    public AuthenticationResponse register(RegisterRequest request) {
//by default, we are assigning all registered user by Role of
// USER Retrieved from database
        Optional<Role> getRole= roleRepository.findByName("USER");
        if (getRole.isEmpty()) {
            return null;
        }
//        now here we are creating a user and build the user using the
//        requests from frontend or postman
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(getRole.get())
                .build();
        repository.save(user);
//        and here we are generating a token for the created user
        var jwtToken = service.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//       validating the user orElseThrows Authentication Exception
       manager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getEmail(),
                       request.getPassword()
               )
       );
       var user = repository.findByEmail(request.getEmail())
               .orElseThrow(
                       ()-> new UsernameNotFoundException("user not found")
               );
        var jwtToken = service.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
