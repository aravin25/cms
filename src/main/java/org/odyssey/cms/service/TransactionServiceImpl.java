package org.odyssey.cms.service;

import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    public TransactionRepository transactionRepository;
    @Override
    public Transaction createTransaction(Transaction newTransaction)throws AccountException {
        Optional<Transaction> transaction = this.transactionRepository.findById(newTransaction.getTransactionID());
        if(transaction.isPresent()){
            throw new AccountException("transaction already exists!");
        }
        return this.transactionRepository.save(newTransaction);
    }

    @Override
    public Transaction getTransactionById(Integer transactionId)throws AccountException {
        Optional<Transaction> transaction = this.transactionRepository.findById(transactionId);
        if(!transaction.isPresent()){
            throw new AccountException("transaction not exists!");
        }
        return this.transactionRepository.findById(transactionId).get();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }
}
