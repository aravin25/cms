package org.odyssey.cms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.TransactionException;
import org.odyssey.cms.repository.AccountRepository;
import org.odyssey.cms.repository.CreditCardRepository;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.TransactionRepository;
import org.odyssey.cms.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PaymentRequestRepository paymentRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private CreditCardService creditCardService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
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
        } catch (TransactionException |  e) {
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
