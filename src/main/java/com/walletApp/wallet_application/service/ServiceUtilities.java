package com.walletApp.wallet_application.service;

import com.walletApp.wallet_application.com.jwtAuthentication.AuthenticationConfig.JwtService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ServiceUtilities {


    public static String getUsername (String token, JwtService service) {

          return service.extractUsername(token);
    }
}
