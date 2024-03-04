package org.odyssey.cms.controller;

import org.odyssey.cms.dto.CreditCardDTO;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.repository.CreditCardRepository;
import org.odyssey.cms.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("creditCard")
@RestController
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("createCreditCard")
    public CreditCard createCreditCard(@RequestBody CreditCardDTO creditCardDTO) throws AccountException {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardId(0);
        creditCard.setPinNumber(creditCardDTO.getPinNumber());
        return this.creditCardService.createCreditCard(creditCard);

    }

    @GetMapping("getAllCreditCard")
    public List<CreditCard> getAllCreditCard()throws AccountException{
        return this.creditCardService.getAllCreditCards();
    }

    @PutMapping("update/{cardId}/activationStatus")
    public CreditCard updateActivationStatus(@PathVariable Integer cardId, @RequestBody String newActivationStatus) throws AccountException{
        return this.creditCardService.updateActivationStatus(cardId, newActivationStatus);
    }

    @DeleteMapping("delete/{cardId}")
    public CreditCard deleteCreditCard(@PathVariable Integer cardId) throws AccountException{
        return this.creditCardService.deleteByCreditCard(cardId);
    }
}