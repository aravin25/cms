package org.odyssey.cms.service;

import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.LastPayment;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.repository.AccountRepository;
import org.odyssey.cms.repository.LastPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LastPaymentRepository lastPaymentRepository;

    @Override
    public Account createAccount(Account newAccount, String type) throws AccountException, CreditCardException {
        Optional<Account> optionalAccount = this.accountRepository.findById(newAccount.getAccountId());
        if(optionalAccount.isPresent()){
            throw new AccountException("Account already exists!");
        }
        newAccount.setOpenDate(LocalDate.now());

        if (type.equals("Customer")) {
            CreditCard creditCard = new CreditCard();
            creditCard.setVendor("Visa");
            creditCard.setBank(newAccount.getBankType());
            CreditCard creditCard1 = creditCardService.createCreditCard(creditCard);
//            List<CreditCard> creditCards = new ArrayList<>();
//            creditCards.add(creditCard1);
//            newAccount.setCreditCards(creditCards);
            creditCard1.setAccount(newAccount);
        }
        //notificationService.saveNotification(newAccount.getUser().getUserId(),"Account","account created");

        return this.accountRepository.save(newAccount);
    }

    @Override
    public List<Account> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    @Override
    public Account getAccountById(Integer accountId) throws AccountException {
        Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
        if(optionalAccount.isEmpty()){
            throw new AccountException("Account does not exist!");
        }
        return optionalAccount.get();
    }

    @Override
    public Account updateAccount(Account account) throws AccountException {
        Optional<Account> optionalAccount = this.accountRepository.findById(account.getAccountId());
        if(optionalAccount.get().getUser().getLogin()==false){
            throw new AccountException("Not Login");
        }
        if(optionalAccount.isEmpty()){
            throw new AccountException("Account does not exist!");
        }
        notificationService.saveNotification(account.getUser().getUserId(),"Account","account Updated");
        return this.accountRepository.save(account);
    }

    @Override
    public Account deleteAccountById(Integer id)throws AccountException{
        Optional<Account> accountOpt = this.accountRepository.findById(id);
        if(accountOpt.get().getUser().getLogin()==false){
            throw new AccountException("Not Login");
        }
        if(accountOpt.isEmpty()){
            throw new AccountException("Account does not exist.");
        }
        this.accountRepository.deleteById(id);
        notificationService.saveNotification(accountOpt.get().getUser().getUserId(),"Account","account Deleted");
        return accountOpt.get();
    }

    @Override
    public Boolean createLasteDate(LastPayment lastPayment) throws AccountException {
        Optional<LastPayment> lastPayments=lastPaymentRepository.findByAccountId(lastPayment.getAccountId());
        if(lastPayments.isEmpty()) {
            lastPaymentRepository.save(lastPayment);
        }
        else{
            lastPaymentRepository.delete(lastPayment);
            lastPaymentRepository.save(lastPayment);
        }
        return true;
    }

    @Override
    public LastPayment getLastDate(Integer accountId) throws AccountException {
        Optional<LastPayment> lastPayments=lastPaymentRepository.findByAccountId(accountId);
        if (lastPayments.isEmpty()){
            throw new AccountException("No Last Payment");
        }
        return lastPayments.get();
    }

}
