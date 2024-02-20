package org.odyssey.cms.service;


import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    PaymentRequestRepository paymentRequestRepository;

    String expectedPin = "xyz@123";

    public boolean authPin(String inputPin) {
        return inputPin.equals(expectedPin);
    }

    public boolean processTransaction(Integer customerId, CreditCard creditCard) {
        Double transactionAmount = paymentRequestRepository.findById(customerId).get().getRequestAmount();
        if(transactionAmount <= 0) {
//            System.out.println("Not possible");
            return false;
        } else if(transactionAmount > creditCard.getCreditLimit() - creditCard.getCreditBalance()) {
//            System.out.println("Transaction amount is exceeding the credit limit.");
            return false;
        } else {
            creditCard.setCreditLimit(creditCard.getCreditLimit()-transactionAmount);
            creditCard.setCreditBalance(creditCard.getCreditBalance()-transactionAmount);
            System.out.println("The transaction was successful.");
            return true;
        }
    }
}
