package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;

import java.time.LocalDate;
import java.util.List;

public interface CreditCardService {
<<<<<<< Updated upstream
    CreditCard createCreditCard(CreditCard creditCard);
    CreditCard getCreditCardById(Integer cardNumber);
    List<CreditCard> getAllCreditCards();
=======
    CreditCard getCreditCardById(Integer cardNumber);
    List<CreditCard> getAllCreditCards();
    CreditCard createCreditCard(CreditCard creditCard);
>>>>>>> Stashed changes
    public CreditCard updateExpireDate(Integer cardNumber, LocalDate newExpireDate);
    public CreditCard updateAmount(Integer cardNumber, Double newAmount);
    public CreditCard updateActivationStatus(Integer cardNumber, String newActivationStatus);
    void deleteCreditCard(Integer cardNumber);
}
