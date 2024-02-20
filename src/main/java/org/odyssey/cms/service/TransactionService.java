package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;

public interface TransactionService {
    public boolean authPin(String inputPin);
    public boolean processTransaction(Integer customerId, CreditCard creditCard);
}
