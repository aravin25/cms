package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import java.util.List;

public interface TransactionService {
    public boolean authPin(String inputPin);
    
    public boolean processTransaction(Integer customerId, CreditCard creditCard);

    Transaction createTransaction(Transaction newTransaction)throws AccountException;

    Transaction getTransactionById(Integer transactionId)throws AccountException;

    List<Transaction> getAllTransactions();

	public List<Transaction> getTransactionsFromThisCycle();

	String creditBalancePayment(Integer customerId, String password, Double amount) throws AccountException, CreditCardException;
}
