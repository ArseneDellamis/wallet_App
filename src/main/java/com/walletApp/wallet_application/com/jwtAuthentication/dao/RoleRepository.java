package com.walletApp.wallet_application.com.jwtAuthentication.dao;



import com.walletApp.wallet_application.com.jwtAuthentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// data access object for role table. used for accessing database table role using Java persistent Api
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
