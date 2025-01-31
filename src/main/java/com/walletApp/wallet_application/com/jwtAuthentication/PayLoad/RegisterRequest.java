package com.walletApp.wallet_application.com.jwtAuthentication.PayLoad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

//registration request class
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
}
