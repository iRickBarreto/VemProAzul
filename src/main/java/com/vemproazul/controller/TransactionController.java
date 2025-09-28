package com.vemproazul.controller;

import com.vemproazul.entity.Transaction;
import com.vemproazul.entity.User;
import com.vemproazul.service.TransactionService;
import com.vemproazul.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping("/create/{userId}")
    public Transaction createTransaction(@PathVariable Long userId, @RequestBody Transaction transaction) {
        Optional<User> userOpt = userService.findById(userId);
        userOpt.ifPresent(transaction::setUser);
        return transactionService.saveTransaction(transaction);
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getTransactions(@PathVariable Long userId) {
        return userService.findById(userId)
                .map(transactionService::getTransactionsByUser)
                .orElse(List.of());
    }

    @GetMapping("/balance/{userId}")
    public Map<String, Double> getBalance(@PathVariable Long userId) {
        Map<String, Double> response = new HashMap<>();
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isPresent()) {
            response.put("balance", transactionService.getBalance(userOpt.get()));
        } else {
            response.put("balance", 0.0);
        }
        return response;
    }
}
