package org.odyssey.cms.service;

import org.odyssey.cms.dto.TransactionDTO;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.TransactionException;
import org.odyssey.cms.exception.UserException;

import java.util.List;

public interface TransactionService {
    public boolean processTransaction(TransactionDTO transactionDTO) throws PaymentRequestException, AccountException, CreditCardException, UserException, TransactionException;

    Transaction createTransaction(Transaction newTransaction) throws AccountException, TransactionException;

    Transaction getTransactionById(Integer transactionId) throws AccountException, TransactionException;

    List<Transaction> getAllTransactions();

	void creditBalancePayment(Integer customerId, String password, Double amount) throws AccountException, CreditCardException;
}
