package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.UserException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface CreditCardService {
    CreditCard getCreditCardById(Integer cardId) throws AccountException, CreditCardException;
    List<CreditCard> getAllCreditCards();
    CreditCard createCreditCard(CreditCard creditCard)throws AccountException;
    public CreditCard updateAmount(Integer cardId, Double newAmount) throws AccountException, CreditCardException;
    public CreditCard updateActivationStatus(Integer cardId, String newActivationStatus) throws AccountException, CreditCardException;
    CreditCard deleteByCardId(Integer cardId) throws AccountException, CreditCardException;
    List<CreditCard> getAllAccounts();

	CreditCard getCreditCardByUserId(Integer userId) throws UserException, AccountException, CreditCardException;
}
