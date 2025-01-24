package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired  
    AccountRepository accRepo;

    public Account registration(Account acc) {
        if(accRepo.existsAccountByUsername(acc.getUsername()))
            return null;

        return accRepo.save(acc);
    }
}
