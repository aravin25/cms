package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CreditCardService {
    CreditCard getCreditCardById(Integer cardId)throws AccountException;
    List<CreditCard> getAllCreditCards();
    CreditCard createCreditCard(CreditCard creditCard)throws AccountException;
    //public CreditCard updateExpireDate(Integer cardId, LocalDate newExpireDate)throws AccountException;
    public CreditCard updateAmount(Integer cardId, Double newAmount)throws AccountException;
    public CreditCard updateActivationStatus(Integer cardId, String newActivationStatus)throws AccountException;
    CreditCard deleteByCreditCard(Integer cardId)throws AccountException;
    List<CreditCard> getAllAccounts();
}
