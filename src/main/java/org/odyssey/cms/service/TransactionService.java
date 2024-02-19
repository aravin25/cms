package org.odyssey.cms.service;

import org.odyssey.cms.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction newTransaction);

    Transaction getTransactionById(Integer transactionId);

    List<Transaction> getAllTransactions();

}
