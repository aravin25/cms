package org.odyssey.cms.controller;

import org.odyssey.cms.dto.CreditBalancePaymentDTO;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("createTransaction")
    public Transaction createTransaction(@RequestBody Transaction transaction)throws AccountException {
        return this.transactionService.createTransaction(transaction);
    }

    @GetMapping("getTransaction/{id}")
    public Transaction getTransactionById(@PathVariable("id") Integer transactionId)throws AccountException{
        return this.transactionService.getTransactionById(transactionId);
    }

    @GetMapping("getAllTransactions")
    public List<Transaction> getAllTransactions(){
        return this.transactionService.getAllTransactions();
    }

    @PostMapping("cms/transaction/creditBalancePayment")
    public void creditBalancePayment(@RequestBody CreditBalancePaymentDTO creditBalancePaymentDTO) throws AccountException, CreditCardException {
        this.transactionService.creditBalancePayment(creditBalancePaymentDTO.getAccountId(), creditBalancePaymentDTO.getPassword(), creditBalancePaymentDTO.getAmount());
    }
}
