package org.odyssey.cms.controller;

import jakarta.validation.Valid;
import org.odyssey.cms.dto.CreditCardDTO;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("creditcard")
@RestController
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("createCreditCard")
    public CreditCard createCreditCard(@Valid @RequestBody CreditCardDTO creditCardDTO) throws AccountException, CreditCardException {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardId(0);
        creditCard.setPinNumber(creditCardDTO.getPinNumber());
        return this.creditCardService.createCreditCard(creditCard);

    }

    @GetMapping("getAllCreditCard")
    public List<CreditCard> getAllCreditCard()throws AccountException{
        return this.creditCardService.getAllCreditCards();
    }

    @GetMapping("creditCard/user")
    public CreditCard getCreditCardByUserId(Integer userId) throws CreditCardException, AccountException, UserException
    {
        return this.creditCardService.getCreditCardByUserId(userId);
    }

    @PutMapping("updateActivationStatus/{cardNumber}")
    public CreditCard updateActivationStatus(@PathVariable String cardNumber, @RequestParam String newActivationStatus) throws AccountException, CreditCardException {
        return this.creditCardService.updateActivationStatus(cardNumber, newActivationStatus);
    }

    @DeleteMapping("delete/{cardNumber}")
    public String deleteCreditCard(@PathVariable String cardNumber) throws AccountException, CreditCardException{
        return this.creditCardService.deleteByCreditCard(cardNumber);
    }

    @PutMapping("PinGeneratation/{firstHalf}/{cardId}")
    public String pinGeneratations(@PathVariable Integer firstHalf,@PathVariable Integer cardId)throws CreditCardException{
        return  creditCardService.pinGeneratation(firstHalf,cardId);
    }
}
