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
import org.odyssey.cms.entity.CreditCardQueue;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.CreditCardQueueException;
import org.odyssey.cms.repository.AdminRepository;
import org.odyssey.cms.repository.CreditCardQueueRepository;
import org.odyssey.cms.repository.CreditCardRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    CreditCard creditCard;
    CreditCard creditCard1;
    CreditCardQueue creditCardQueue;
    CreditCardQueue emptyCreditCardQueue;
    List<CreditCardQueue> creditCardQueueList;
    List<CreditCardQueue> emptyCreditCardQueueList;
    Account account;
    Transaction transaction;
    List<Transaction> transactionList = new ArrayList<>();
    @Mock
    AdminRepository adminRepository;
    @Mock
    CreditCardRepository creditCardRepository;
    @Mock
    CreditCardQueueRepository creditCardQueueRepository;
    @Mock
    CreditCardService creditCardService;
    @InjectMocks
    AdminServiceImpl adminService;

    @BeforeEach
    public void setup(){
        account = new Account();
        transaction = new Transaction();
        creditCard = new CreditCard(1, "696969", LocalDate.now(), 123, 1234.0, 5000.0, "True", 1234, 0.018, "Visa", account, transactionList);
        creditCard1 = new CreditCard();
        creditCardQueue = new CreditCardQueue(1, "6969696");
        creditCardQueueList = new ArrayList<>();
        creditCardQueueList.add(creditCardQueue);
        emptyCreditCardQueueList = new ArrayList<>();
    }


    @Test
    public void approveAllCreditCardTest() throws AccountException, CreditCardException, CreditCardQueueException
    {
        when(creditCardQueueRepository.findAll()).thenReturn(creditCardQueueList);
        String response = adminService.approveAllCreditCard();
        Assertions.assertEquals(response, "ACTIVATION COMPLETED");
        verify(creditCardQueueRepository).findAll();

    }


    @Test
    public void approveAllCreditCardQueueEmptyTest()
    {
        when(creditCardQueueRepository.findAll()).thenReturn(emptyCreditCardQueueList);
        Assertions.assertThrows(CreditCardQueueException.class, () -> adminService.approveAllCreditCard());
        verify(creditCardQueueRepository).findAll();
    }

    @Test
    public void approveIndividualCreditCardTest() throws AccountException, CreditCardException, CreditCardQueueException
    {
        when(creditCardQueueRepository.findByCreditCardNumber("696969")).thenReturn(Optional.of(creditCardQueue));
        when(creditCardService.updateActivationStatus("696969", "ACTIVATED")).thenReturn(creditCard);
        String response = adminService.approveIndividualCreditCard("696969");
        Assertions.assertEquals(response, "ACTIVATION COMPLETED");
        verify(creditCardQueueRepository).findByCreditCardNumber("696969");
        verify(creditCardService).updateActivationStatus("696969", "ACTIVATED");
    }

    @Test
    public void approveIndividualCreditCardEmptyQueueTest()
    {
        when(creditCardQueueRepository.findByCreditCardNumber("696969")).thenReturn(Optional.ofNullable(emptyCreditCardQueue));
        Assertions.assertThrows(CreditCardQueueException.class, () -> adminService.approveIndividualCreditCard("696969"));
        verify(creditCardQueueRepository).findByCreditCardNumber("696969");
    }
}
