package com.walletApp.wallet_application.com.jwtAuthentication.service;


import com.walletApp.wallet_application.com.jwtAuthentication.AuthenticationConfig.JwtService;
import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.AuthenticationRequest;
import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.AuthenticationResponse;
import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.RegisterRequest;
import com.walletApp.wallet_application.com.jwtAuthentication.PayLoad.UpdateRequest;
import com.walletApp.wallet_application.com.jwtAuthentication.Role;
import com.walletApp.wallet_application.com.jwtAuthentication.User;
import com.walletApp.wallet_application.com.jwtAuthentication.dao.RoleRepository;
import com.walletApp.wallet_application.com.jwtAuthentication.dao.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import static com.walletApp.wallet_application.service.ServiceUtilities.*;

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

        // Check if the email already exists in the database.
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

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

    public User updateAuthenticatedUserInfo(String token, @NotNull UpdateRequest request) {
        String username = getUsername(token, service);
        User user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New and current password should not match!");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return repository.save(user);
    }

    public User getUserDetails(String token) {
        String user = getUsername(token, service);

        return checkIfUserExists(user, repository);
    }
}
