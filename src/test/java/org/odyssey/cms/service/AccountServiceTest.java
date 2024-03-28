package org.odyssey.cms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.repository.AccountRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    Account account;
    Account account1;
    CreditCard creditCard;
    CreditCard creditCard1;
    List<CreditCard> creditCardList;
    List<CreditCard> creditCardList1;
    User user;
    User user1;
    List<Account> allAccounts = new ArrayList<>();
    @Mock
    AccountRepository accountRepository;
    @Mock
    CreditCardService creditCardService;
    @Mock
    NotificationService notificationService;
    @InjectMocks
    AccountServiceImpl accountService;

    @BeforeEach
    public void setup(){
        user = new User();
        user1 = new User();
        creditCard = new CreditCard();
        creditCard1 = new CreditCard();
        creditCardList.add(creditCard);
        creditCardList1.add(creditCard1);

        account = new Account(1, 10000.0, LocalDate.now(), "Abec@12345", "sbi", user, creditCardList);
        account1 = new Account(2, 15000.0, LocalDate.now(), "Abec@12345", "sbi", user1, creditCardList1);
        allAccounts.add(account);
        allAccounts.add(account1);
    }

    @Test
    public void createAccountTest() throws AccountException, CreditCardException {
        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.empty());
        when(creditCardService.createCreditCard(any())).thenReturn(creditCard);
        Account response = accountService.createAccount(account, "Customer");
        Assertions.assertEquals(null, response);
    }

    @Test
    public void createExistingAccountTest() throws AccountException{
        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));
        Assertions.assertThrows(AccountException.class, () -> accountService.createAccount(account, "Customer"));
    }

    @Test
    public void getAllAccountsTest(){
        when(accountRepository.findAll()).thenReturn(allAccounts);
        List<Account> response = accountService.getAllAccounts();
        Assertions.assertEquals(response, allAccounts);
    }

    @Test
    public void getAccountByIdTest() throws AccountException {
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(account));
        Account response = accountService.getAccountById(1);
        Assertions.assertEquals(response, account);
    }

    @Test
    public void getAccountByIdAccountExceptionTest() throws AccountException{
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(AccountException.class, () -> accountService.getAccountById(anyInt()));
    }

    @Test
    public void updateAccountTest() throws AccountException {
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        when(notificationService.saveNotification(any(), any(), any())).thenReturn(true);
        Account response = accountService.updateAccount(account);
        Assertions.assertEquals(null, response);
    }

    @Test
    public void updateAccountAccountDoesNotExistTest() throws Exception{
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(AccountException.class, () -> accountService.updateAccount(account));
    }

    @Test
    public void deleteAccountByIdTest() throws AccountException {
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        Account response = accountService.deleteAccountById(1);
        Assertions.assertEquals(response, account);
    }

    @Test
    public void deleteAccountByIdAccountDoesNotExistTest() throws AccountException{
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(AccountException.class, () -> accountService.deleteAccountById(anyInt()));
    }
}
