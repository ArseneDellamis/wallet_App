package com.walletApp.wallet_application.service;

import com.walletApp.wallet_application.com.jwtAuthentication.AuthenticationConfig.JwtService;
import com.walletApp.wallet_application.com.jwtAuthentication.User;
import com.walletApp.wallet_application.com.jwtAuthentication.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@RequiredArgsConstructor
public class ServiceUtilities {



    public static String getUsername (String token, @NotNull JwtService service) {

          return service.extractUsername(token);
    }

    public static User checkIfUserExists(String username, @NotNull UserRepository repo){

        return repo.findByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException("username not found")
        );
    }
}
