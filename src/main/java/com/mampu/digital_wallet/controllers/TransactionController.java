package com.mampu.digital_wallet.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mampu.digital_wallet.helpers.ResponseApi;
import com.mampu.digital_wallet.models.entities.Transaction;
import com.mampu.digital_wallet.models.entities.TransactionType;
import com.mampu.digital_wallet.models.entities.User;
import com.mampu.digital_wallet.services.TransactionService;
import com.mampu.digital_wallet.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> findAll(@PathVariable String userId) {
        List<Transaction> transactions = transactionService.findByUserId(userId);
        
        if (transactions.isEmpty()) {
            return ResponseEntity.status(404).body(new ResponseApi(false, "Transactions not found", null));
        }
        return ResponseEntity.ok(new ResponseApi(true, "Successfully retrieved transactions", transactions));
    }

    
    @Operation(summary = "Deposit Funds", description = "Allows a user to deposit a certain amount")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deposited"),
        @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User ID and amount to deposit",
                    required = true,
                    content = @Content(
                            schema = @Schema(example = "{\"userId\": \"afffc51f-ffa3-463e-83bc-1e422bdae5e8\", \"amount\": 100000}")
                    )
            )
            @RequestBody Map<String, Object> requestBody) {
        return handleTransaction(requestBody, TransactionType.IN, "Successfully deposited");
    }

    @Operation(summary = "Withdraw Funds", description = "Allows a user to withdraw a certain amount")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully withdraw"),
        @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User ID and amount to withdraw",
                    required = true,
                    content = @Content(
                            schema = @Schema(example = "{\"userId\": \"afffc51f-ffa3-463e-83bc-1e422bdae5e8\", \"amount\": 100000}")
                    )
            )
            @RequestBody Map<String, Object> requestBody) {
        return handleTransaction(requestBody, TransactionType.OUT, "Successfully withdraw");
    }

    private ResponseEntity<?> handleTransaction(Map<String, Object> requestBody, TransactionType type, String successMessage) {
        String userId = (String) requestBody.get("userId");
        Double amount = extractAmount(requestBody.get("amount"));

        if (userId == null || amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body(new ResponseApi(false, "Invalid userId or amount", null));
        }

        User user = userService.findOne(userId);
        if (user == null) {
            return ResponseEntity.status(404).body(new ResponseApi(false, "User not found", null));
        }

        if (type == TransactionType.OUT) {
            Double balance = transactionService.getBalance(user.getId());
            if (balance < amount) {
                return ResponseEntity.status(400).body(new ResponseApi(false, "Insufficient balance", null));
            }
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(type);
        transaction.setAmount(amount);
        transactionService.save(transaction);

        return ResponseEntity.ok(new ResponseApi(true, successMessage, transaction));
    }

    private Double extractAmount(Object amountObj) {
        return (amountObj instanceof Number) ? ((Number) amountObj).doubleValue() : null;
    }
}
