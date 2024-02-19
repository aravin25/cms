package org.odyssey.cms.service;

import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    public TransactionRepository transactionRepository;
    @Override
    public Transaction createTransaction(Transaction newTransaction) {
        return this.transactionRepository.save(newTransaction);
    }

    @Override
    public Transaction getTransactionById(Integer transactionId) {
        return this.transactionRepository.findById(transactionId).get();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }
}
