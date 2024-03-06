package org.odyssey.cms.service;

import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.TransactionException;
import org.odyssey.cms.repository.AccountRepository;
import org.odyssey.cms.repository.CreditCardRepository;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.TransactionRepository;
import org.odyssey.cms.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private CreditCardRepository creditCardRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private PaymentRequestRepository paymentRequestRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CreditCardService creditCardService;
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private TransactionServiceImpl transactionService;

    User user;
    Account account;
    CreditCard creditCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setUserId(1);

        account = new Account();
        account.setAccountId(1);
        account.setBalance(10000.0);
        account.setPassword("xcv");
        account.setUser(user);

        creditCard = new CreditCard();
        creditCard.setActivationStatus("REQUESTED");
        creditCard.setCreditBalance(100.0);
        account.setCreditCard(creditCard);
    }

    @Test
	void creditBalancePaymentAccountDoesntExist() {
		when(accountRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(AccountException.class,
                () -> this.transactionService.creditBalancePayment(1, "xcv", 100.0)
        );

        verify(accountRepository).findById(any());
	}

    @Test
    void creditBalancePaymentAccountBalanceNotEnough() {
        account.setBalance(100.0);

        when(accountRepository.findById(any())).thenReturn(Optional.of(account));

        assertThrows(AccountException.class,
                () -> this.transactionService.creditBalancePayment(1, "xcv", 1000.0)
        );

        verify(accountRepository).findById(any());
    }

    @Test
    void creditBalancePaymentAccountPasswordWrong() {
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));

        assertThrows(AccountException.class,
                () -> this.transactionService.creditBalancePayment(1, "wer", 1000.0)
        );

        verify(accountRepository).findById(any());
    }

    @Test
    void creditBalancePaymentCreditCardNull() {
        creditCard = null;

        when(accountRepository.findById(any())).thenReturn(Optional.of(account));

        assertThrows(CreditCardException.class,
                () -> this.transactionService.creditBalancePayment(1, "xcv", 1000.0),
                "Credit card should exist to pay credit balance"
        );

        verify(accountRepository).findById(any());
    }

    @Test
    void creditBalancePaymentCreditCardNotActive() {
        when(accountRepository.findById(any())).thenReturn(Optional.of(account));

        assertThrows(CreditCardException.class,
                () -> this.transactionService.creditBalancePayment(1, "xcv", 1000.0),
                "Credit card is not activated yet"
        );

        verify(accountRepository).findById(any());
    }

    @Test
    void creditBalancePaymentAmountMoreThanCreditBalance() {
        creditCard.setActivationStatus("ACTIVATED");

        when(accountRepository.findById(any())).thenReturn(Optional.of(account));

        assertThrows(AccountException.class,
                () -> this.transactionService.creditBalancePayment(1, "xcv", 1000.0),
                "User is paying more than the outstanding credit balance"
        );

        verify(accountRepository).findById(any());
    }

    @SneakyThrows
    @Test
    void creditBalancePaymentAmountNullSuccess() {
        creditCard.setActivationStatus("ACTIVATED");

        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        when(notificationService.saveNotification(any(), any(), any())).thenReturn(true);

        this.transactionService.creditBalancePayment(1, "xcv", null);

        verify(accountRepository).findById(any());
        verify(creditCardRepository, atLeast(1)).save(any());
        verify(accountRepository, atLeast(1)).save(any());
    }

    @SneakyThrows
    @Test
    void creditBalancePaymentSuccess() {
        creditCard.setActivationStatus("ACTIVATED");

        when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        when(notificationService.saveNotification(any(), any(), any())).thenReturn(true);

        this.transactionService.creditBalancePayment(1, "xcv", 50.0);

        verify(accountRepository).findById(any());
        verify(creditCardRepository, atLeast(1)).save(any());
        verify(accountRepository, atLeast(1)).save(any());
    }

    @Test
    void testCreateTransaction_Success() {
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionID(1);
        newTransaction.setTransactionDateTime(LocalDateTime.now());

        when(transactionRepository.findById(newTransaction.getTransactionID())).thenReturn(Optional.empty());
        when(transactionRepository.save(newTransaction)).thenReturn(newTransaction);

        try {
            Transaction createdTransaction = transactionService.createTransaction(newTransaction);
            assertNotNull(createdTransaction);
            assertEquals(newTransaction.getTransactionID(), createdTransaction.getTransactionID());
        } catch (TransactionException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    void testCreateTransaction_TransactionAlreadyExists() {
        Transaction existingTransaction = new Transaction();
        existingTransaction.setTransactionID(1);
        existingTransaction.setTransactionDateTime(LocalDateTime.now());

        when(transactionRepository.findById(existingTransaction.getTransactionID())).thenReturn(Optional.of(existingTransaction));

        assertThrows(TransactionException.class, () -> {
            transactionService.createTransaction(existingTransaction);
        });
    }

    @Test
    void testGetTransactionById_Success() {
        Transaction existingTransaction = new Transaction();
        existingTransaction.setTransactionID(1);
        existingTransaction.setTransactionDateTime(LocalDateTime.now());

        when(transactionRepository.findById(existingTransaction.getTransactionID())).thenReturn(Optional.of(existingTransaction));

        try {
            Transaction retrievedTransaction = transactionService.getTransactionById(existingTransaction.getTransactionID());
            assertNotNull(retrievedTransaction);
            assertEquals(existingTransaction.getTransactionID(), retrievedTransaction.getTransactionID());
        } catch (TransactionException e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    void testGetTransactionById_TransactionDoesNotExist() {
        int nonExistentTransactionId = 100;

        when(transactionRepository.findById(nonExistentTransactionId)).thenReturn(Optional.empty());

        assertThrows(TransactionException.class, () -> {
            transactionService.getTransactionById(nonExistentTransactionId);
        });
    }

    @Test
    void testGetAllTransactions() {
        List<Transaction> mockTransactionList = new ArrayList<>();
        mockTransactionList.add(new Transaction());
        mockTransactionList.add(new Transaction());
        mockTransactionList.add(new Transaction());

        when(transactionRepository.findAll()).thenReturn(mockTransactionList);

        List<Transaction> retrievedTransactions = transactionService.getAllTransactions();
        assertNotNull(retrievedTransactions);
        assertEquals(mockTransactionList.size(), retrievedTransactions.size());
    }

    @Test
    void creditBalancePaymentTest() {

    }
}
