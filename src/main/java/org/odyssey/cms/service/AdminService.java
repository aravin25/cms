package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCardQueue;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.CreditCardQueueException;
import org.odyssey.cms.exception.UserException;

import java.util.List;

public interface AdminService {
    public List<CreditCardQueue> getAllCreditCardQueue();
    public String approveAllCreditCard() throws AccountException, CreditCardQueueException, CreditCardException;
    public String approveIndividualCreditCard(String creditCardNumber) throws AccountException, CreditCardQueueException, CreditCardException;
    public String logInAdmin(String email, String password) throws UserException;
}
