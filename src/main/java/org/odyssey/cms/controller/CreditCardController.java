package org.odyssey.cms.controller;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.repository.CreditCardRepository;
import org.odyssey.cms.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @PostMapping("/createCard")
    public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard creditCard) {
        CreditCard createdCreditCard = creditCardService.createCreditCard(creditCard);
        return new ResponseEntity<>(createdCreditCard, HttpStatus.CREATED);
    }

    @PutMapping("/{cardNumber}/expire-date")
    public ResponseEntity<CreditCard> updateExpireDate(@PathVariable Integer cardNumber, @RequestBody LocalDate newExpireDate) {
        CreditCard updatedCreditCard = creditCardService.updateExpireDate(cardNumber, newExpireDate);
        if (updatedCreditCard != null) {
            return new ResponseEntity<>(updatedCreditCard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{cardNumber}/activation-status")
    public ResponseEntity<CreditCard> updateActivationStatus(@PathVariable Integer cardNumber, @RequestBody String newActivationStatus) {
        CreditCard updatedCreditCard = creditCardService.updateActivationStatus(cardNumber, newActivationStatus);
        if (updatedCreditCard != null) {
            return new ResponseEntity<>(updatedCreditCard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cardNumber}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Integer cardNumber) {
        creditCardService.deleteCreditCard(cardNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
