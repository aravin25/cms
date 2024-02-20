package org.odyssey.cms.controller;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("cms/transaction")
    public boolean transactionInitiate(@RequestParam String inputPin, @RequestParam Integer customerId, @RequestBody CreditCard creditCard){
        boolean flag = false;
        if(transactionService.authPin(inputPin)) {
            flag = transactionService.processTransaction(customerId, creditCard);
        }
        return flag;
    }
}
