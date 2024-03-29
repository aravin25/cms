package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.UserException;
import org.springframework.stereotype.Service;

import javax.swing.undo.CannotRedoException;
import java.time.LocalDate;
import java.util.List;

@Service
public interface CreditCardService {
    CreditCard getCreditCardById(String cardNumber) throws AccountException, CreditCardException;
    List<CreditCard> getAllCreditCards();
    CreditCard createCreditCard(CreditCard creditCard) throws AccountException, CreditCardException;

    CreditCard createCreditCardByAccountId (CreditCard creditCard, Integer accountId) throws CreditCardException, AccountException;
    public CreditCard updateExpireDate(String cardNumber, LocalDate newExpireDate) throws AccountException, CreditCardException;
    public CreditCard updateAmount(String cardNumber, Double newAmount) throws AccountException, CreditCardException;
    public CreditCard updateActivationStatus(String cardNumber, String newActivationStatus) throws AccountException, CreditCardException;
    String deleteByCreditCard(String cardNumber) throws AccountException, CreditCardException;
    List<CreditCard> getAllAccounts();
    String pinGeneratation(Integer firstHalf,Integer cardId)throws CreditCardException;

	List<CreditCard> getCreditCardsByUserId(Integer userId) throws UserException, AccountException, CreditCardException;
}
