package com.mampu.digital_wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mampu.digital_wallet.helpers.ResponseApi;
import com.mampu.digital_wallet.models.entities.User;
import com.mampu.digital_wallet.services.TransactionService;
import com.mampu.digital_wallet.services.UserService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@RequestMapping("/inquiry")
public class InquiryController {
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getBalance(@PathVariable String userId) {
        User user = userService.findOne(userId);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseApi(false, "User not found", null)
            );
        }

        Double balance = transactionService.getBalance(userId);
        UserBalanceResponse response = new UserBalanceResponse(user.getAccountNumber(), user.getName(), balance);
        
        return ResponseEntity.ok(new ResponseApi(true, "Successfully retrieved balance", response));
    }

    @Getter
    @AllArgsConstructor
    public static class UserBalanceResponse {
        private String accountNumber;
        private String name;
        private Double balance;
    }
}
