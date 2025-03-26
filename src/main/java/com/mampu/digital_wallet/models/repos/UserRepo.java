package com.mampu.digital_wallet.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mampu.digital_wallet.models.entities.User;

public interface UserRepo extends JpaRepository<User, String> {
    User findByAccountNumber(String accountNumber);
}
