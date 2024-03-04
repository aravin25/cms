package org.odyssey.cms.controller;

import org.odyssey.cms.dto.CreditBalancePaymentDTO;
import org.odyssey.cms.dto.TransactionDTO;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.TransactionException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("transaction")
@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @PostMapping("create")
    public Transaction createTransaction(@RequestBody Transaction transaction) throws AccountException, TransactionException {
        return this.transactionService.createTransaction(transaction);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable("id") Integer transactionId) throws AccountException, TransactionException {
        return this.transactionService.getTransactionById(transactionId);
    }

    @GetMapping("all")
    public List<Transaction> getAllTransactions(){
        return this.transactionService.getAllTransactions();
    }

    @PostMapping("creditBalancePayment")
    public void creditBalancePayment(@RequestBody CreditBalancePaymentDTO creditBalancePaymentDTO) throws AccountException, CreditCardException  {
        this.transactionService.creditBalancePayment(creditBalancePaymentDTO.getAccountId(), creditBalancePaymentDTO.getPassword(), creditBalancePaymentDTO.getAmount());
    }
  
    @PostMapping("initiate")
    public boolean transactionInitiate(@RequestBody TransactionDTO transactionDTO) throws AccountException, CreditCardException, PaymentRequestException, UserException, TransactionException {
        return this.transactionService.processTransaction(transactionDTO);
    }
}
