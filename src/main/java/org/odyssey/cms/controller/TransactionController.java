package org.odyssey.cms.controller;

import jakarta.validation.Valid;
import org.odyssey.cms.dto.CreditBalancePaymentDTO;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("transaction")
@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("create")
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction)throws AccountException {
        return this.transactionService.createTransaction(transaction);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable("id") Integer transactionId)throws AccountException{
        return this.transactionService.getTransactionById(transactionId);
    }

    @GetMapping("all")
    public List<Transaction> getAllTransactions(){
        return this.transactionService.getAllTransactions();
    }

    @PostMapping("creditBalancePayment")
    public void creditBalancePayment(@Valid @RequestBody CreditBalancePaymentDTO creditBalancePaymentDTO) throws AccountException, CreditCardException {
        this.transactionService.creditBalancePayment(creditBalancePaymentDTO.getAccountId(), creditBalancePaymentDTO.getPassword(), creditBalancePaymentDTO.getAmount());
    }
  
    @PostMapping("initiate")
    public boolean transactionInitiate(@RequestParam String inputPin, @RequestParam Integer userId, @RequestBody CreditCard creditCard){
        boolean flag = false;
        if(transactionService.authPin(inputPin)) {
            flag = transactionService.processTransaction(userId, creditCard);
        }
        return flag;
    }
}
