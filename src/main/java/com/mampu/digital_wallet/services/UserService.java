package com.mampu.digital_wallet.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.mampu.digital_wallet.models.entities.User;
import com.mampu.digital_wallet.models.repos.UserRepo;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    public User save(String name) {
        User user = new User();
        user.setName(name);
        return userRepo.save(user);
    }

    public Iterable<User> findAll() {
        return userRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public User findOne(String id) {
        Optional<User> user = userRepo.findById(id);
        
        return user.orElse(null);
    }
}
