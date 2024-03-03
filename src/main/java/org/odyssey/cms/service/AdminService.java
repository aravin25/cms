package org.odyssey.cms.service;

import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;

public interface AdminService {

    public String approveAllCreditCard()throws AccountException,NotificationException;
    public String approveIndividualCreditCard(String creditCardNumber) throws AccountException,NotificationException;
}
