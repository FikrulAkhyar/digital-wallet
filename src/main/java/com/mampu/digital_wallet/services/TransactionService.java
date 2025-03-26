package com.mampu.digital_wallet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mampu.digital_wallet.models.entities.Transaction;
import com.mampu.digital_wallet.models.entities.User;
import com.mampu.digital_wallet.models.repos.TransactionRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionService {
    
    @Autowired
    private TransactionRepo transactionRepo;

    public Transaction save(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    public Double getBalance(String userId) {
        return transactionRepo.getBalance(userId);
    }

    public List<Transaction> findByUserId(String userId) {
        return transactionRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
