package com.walletApp.wallet_application.com.jwtAuthentication.dao;


import com.walletApp.wallet_application.com.jwtAuthentication.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
