package org.odyssey.cms.service;

import org.odyssey.cms.exception.AccountException;

public interface AdminService {

    public String approveAllCreditCard()throws AccountException;
    public String approveIndividualCreditCard(Integer cardId) throws AccountException;
}
