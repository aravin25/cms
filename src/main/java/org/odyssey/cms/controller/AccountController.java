package org.odyssey.cms.controller;

import org.odyssey.cms.entity.Account;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("account")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("create")
    public Account createAccount(@RequestBody Account account) throws AccountException {
        return this.accountService.createAccount(account, "Customer");
    }

    @GetMapping("all")
    public List<Account> getAllAccounts(){
        return this.accountService.getAllAccounts();
    }

    @GetMapping("{id}")
    public Account getAccountById(@PathVariable("id") Integer accountId) throws AccountException{
        return this.accountService.getAccountById(accountId);
    }

    @PutMapping("update")
    public Account updateAccount(@RequestBody Account account)throws AccountException{
        return this.accountService.updateAccount(account);
    }

    @DeleteMapping("delete/{id}")
    public Account deleteAccountById(@PathVariable("id") Integer accountId)throws AccountException{
        return this.accountService.deleteAccountById(accountId);
    }

}
