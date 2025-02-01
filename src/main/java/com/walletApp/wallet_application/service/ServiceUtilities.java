package com.walletApp.wallet_application.service;

import com.walletApp.wallet_application.com.jwtAuthentication.AuthenticationConfig.JwtService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;


@RequiredArgsConstructor
public class ServiceUtilities {


    public static String getUsername (String token, @NotNull JwtService service) {

          return service.extractUsername(token);
    }
}
