package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CreditCardService {
    CreditCard getCreditCardById(Integer cardNumber);
    List<CreditCard> getCreditCards();
    List<CreditCard> getAllCreditCards();
    CreditCard createCreditCard(CreditCard creditCard);
    public CreditCard updateExpireDate(Integer cardNumber, LocalDate newExpireDate);
    public CreditCard updateAmount(Integer cardNumber, Double newAmount);
    public CreditCard updateActivationStatus(Integer cardNumber, String newActivationStatus);
    void deleteCreditCard(Integer cardNumber);
}
