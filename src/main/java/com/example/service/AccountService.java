package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public boolean userNameExist(String username) {
        return accountRepository.findByUsername(username) != null;
    }
    // POST /register
    public Account createAccount(Account account) {
        // If blank
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException();
        }
        // if less than 4
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException();
        }
        return accountRepository.save(account);
    }
    // POST /login
    public Account loginAccount(Account account) {
        Account postAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (postAccount == null) {
            throw new IllegalArgumentException();
        }
        return postAccount;
    }
}
