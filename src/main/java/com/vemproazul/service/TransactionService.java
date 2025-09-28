package com.vemproazul.service;

import com.vemproazul.entity.Transaction;
import com.vemproazul.entity.User;
import com.vemproazul.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    public TransactionService(TransactionRepository transactionRepository) { this.transactionRepository = transactionRepository; }

    public Transaction saveTransaction(Transaction transaction) { return transactionRepository.save(transaction); }

    public List<Transaction> getTransactionsByUser(User user) { return transactionRepository.findByUser(user); }

    public Double getBalance(User user) {
        List<Transaction> transactions = transactionRepository.findByUser(user);
        double balance = 0;
        for (Transaction t : transactions) {
            if (t.getType() == com.vemproazul.entity.TransactionType.RECEITA) {
                balance += t.getAmount();
            } else {
                balance -= t.getAmount();
            }
        }
        return balance;
    }
}
