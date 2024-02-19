package org.odyssey.cms.service;

import org.odyssey.cms.entity.Account;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(Account newAccount) throws AccountException{
        Optional<Account> optionalAccount = this.accountRepository.findById(newAccount.getAccountId());
        if(optionalAccount.isPresent()){
            throw new AccountException("Account already exists!");
        }
        return this.accountRepository.save(newAccount);
    }

    @Override
    public List<Account> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Integer accountId){
        return this.accountRepository.findById(accountId).get();
    }

    @Override
    public Account updateAccount(Account account) {
        return this.accountRepository.save(account);
    }

    @Override
    public Account deleteAccountById(Integer id) {
        Optional<Account> accountOpt = this.accountRepository.findById(id);
        // exception handling
        if(!accountOpt.isPresent()){
            System.out.println("Account does not exist.");
        }
        this.accountRepository.deleteById(id);
        return accountOpt.get();
    }

}
