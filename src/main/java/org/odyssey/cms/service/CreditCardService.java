package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.UserException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CreditCardService {
    CreditCard getCreditCardById(Integer cardId)throws AccountException, CreditCardException;
    List<CreditCard> getAllCreditCards();
    CreditCard createCreditCard(CreditCard creditCard)throws AccountException,NotificationException;;
    public CreditCard updateAmount(Integer cardId, Double newAmount)throws AccountException, CreditCardException,NotificationException;
    public CreditCard updateActivationStatus(Integer cardId, String newActivationStatus)throws AccountException, CreditCardException,NotificationException;
    CreditCard deleteByCreditCard(Integer cardId)throws AccountException, CreditCardException,NotificationException;
    List<CreditCard> getAllAccounts();

	CreditCard getCreditCardByUserId(Integer userId) throws UserException, AccountException, CreditCardException;
}
