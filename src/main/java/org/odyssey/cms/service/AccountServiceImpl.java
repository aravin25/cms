package org.odyssey.cms.service;

import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CreditCardService creditCardService;

    @Override
    public Account createAccount(Account newAccount) throws AccountException{
        Optional<Account> optionalAccount = this.accountRepository.findById(newAccount.getAccountId());
        if(optionalAccount.isPresent()){
            throw new AccountException("Account already exists!");
        }
        newAccount.setOpenDate(LocalDate.now());

        CreditCard creditCard = new CreditCard();
        creditCardService.createCreditCard(creditCard);

        newAccount.setCreditCard(creditCard);

        return this.accountRepository.save(newAccount);
    }

    @Override
    public List<Account> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Integer accountId) throws AccountException{
        Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
        if(!optionalAccount.isPresent()){
            throw new AccountException("Account does not exist!");
        }
        return this.accountRepository.findById(accountId).get();
    }

    @Override
    public Account updateAccount(Account account)throws AccountException {
        Optional<Account> optionalAccount = this.accountRepository.findById(account.getAccountId());
        if(!optionalAccount.isPresent()){
            throw new AccountException("Account does not exists!");
        }
        return this.accountRepository.save(account);
    }

    @Override
    public Account deleteAccountById(Integer id)throws AccountException {
        Optional<Account> accountOpt = this.accountRepository.findById(id);
        if(!accountOpt.isPresent()){
            throw new AccountException("Account does not exist.");
        }
        this.accountRepository.deleteById(id);
        return accountOpt.get();
    }

}
