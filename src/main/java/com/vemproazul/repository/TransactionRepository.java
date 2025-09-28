package com.vemproazul.repository;

import com.vemproazul.entity.Transaction;
import com.vemproazul.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
