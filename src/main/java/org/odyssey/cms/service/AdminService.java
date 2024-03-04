package org.odyssey.cms.service;

import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.CreditCardQueueException;

public interface AdminService {
    public String approveAllCreditCard() throws AccountException, CreditCardQueueException, CreditCardException;
    public String approveIndividualCreditCard(String creditCardNumber) throws AccountException, CreditCardQueueException, CreditCardException;
}
