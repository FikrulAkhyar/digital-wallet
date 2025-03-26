package com.mampu.digital_wallet.models.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mampu.digital_wallet.models.entities.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, String> {

    @Query("SELECT COALESCE(SUM(CASE WHEN t.type = 'in' THEN t.amount ELSE -t.amount END), 0) FROM Transaction t WHERE t.user.id = :userId")
    Double getBalance(String userId);

    List<Transaction> findByUserIdOrderByCreatedAtDesc(String userId);
}
