package org.odyssey.cms.controller;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.repository.CreditCardRepository;
import org.odyssey.cms.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("createCreditCard")
    public CreditCard createCreditCard(@RequestBody CreditCard creditCard) throws AccountException {
        return this.creditCardService.createCreditCard(creditCard);

    }

    @GetMapping("getAllCreditCard")
    public List<CreditCard> getAllCreditCard()throws AccountException{
        return this.creditCardService.getAllAccounts();
    }

    @PutMapping("{cardNumber}/putExpireDate")
    public CreditCard updateExpireDate(@PathVariable String cardNumber, @RequestBody LocalDate newExpireDate) throws AccountException{
        return this.creditCardService.updateExpireDate(cardNumber,newExpireDate);
    }

    @PutMapping("/creditCard/{cardNumber}/putActivationStatus")
    public CreditCard updateActivationStatus(@PathVariable String cardNumber, @RequestBody String newActivationStatus) throws AccountException{
        return this.creditCardService.updateActivationStatus(cardNumber, newActivationStatus);
    }

    @DeleteMapping("{cardNumber}")
    public String deleteCreditCard(@PathVariable String cardNumber) throws AccountException{
        return this.creditCardService.deleteByCreditCard(cardNumber);
    }
}
