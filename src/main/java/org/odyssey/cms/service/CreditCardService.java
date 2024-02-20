package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;

import java.time.LocalDate;
import java.util.List;

public interface CreditCardService {
    CreditCard getCreditCardById(String cardNumber)throws AccountException;
    List<CreditCard> getAllCreditCards();
    CreditCard createCreditCard(CreditCard creditCard)throws AccountException;
    public CreditCard updateExpireDate(String cardNumber, LocalDate newExpireDate)throws AccountException;
    public CreditCard updateAmount(String cardNumber, Double newAmount)throws AccountException;
    public CreditCard updateActivationStatus(String cardNumber, String newActivationStatus)throws AccountException;
    String deleteByCreditCard(String cardNumber)throws AccountException;

    List<CreditCard> getAllAccounts();
}
