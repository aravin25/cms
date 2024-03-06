package org.odyssey.cms.controller;

import org.odyssey.cms.dto.CreditCardDTO;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("updateActivationStatus/{cardId}")
    public CreditCard updateActivationStatus(@PathVariable Integer cardId, @RequestBody String newActivationStatus) throws AccountException, CreditCardException {
        return this.creditCardService.updateActivationStatus(cardId, newActivationStatus);
    }

    @DeleteMapping("delete/{cardId}")
    public CreditCard deleteCreditCard(@PathVariable Integer cardId) throws AccountException, CreditCardException{
        return this.creditCardService.deleteByCardId(cardId);
    }
}
