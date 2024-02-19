package org.odyssey.cms.controller;

import org.odyssey.cms.entity.Account;
import org.odyssey.cms.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("createAccount")
    public Account createAccount(@RequestBody Account account){
        return this.accountService.createAccount(account);
    }

    @GetMapping("getAllAccounts")
    public List<Account> getAllAccounts(){
        return this.accountService.getAllAccounts();
    }

    @GetMapping("account/{id}")
    public Account getAccountById(@PathVariable("id") Integer accountId){
        return this.accountService.getAccountById(accountId);
    }

    @PutMapping("updateAccount")
    public Account updateAccount(@RequestBody Account account){
        return this.accountService.updateAccount(account);
    }

    @DeleteMapping("delAccount/{id}")
    public Account deleteAccountById(@PathVariable("id") Integer accountId){
        return this.accountService.deleteAccountById(accountId);
    }

}
