package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCardQueue;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.repository.CreditCardQueueRepository;
import org.odyssey.cms.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CreditCardQueueRepository creditCardQueueRepository;
    @Autowired
    private CreditCardService creditCardService;
    @Override
    public String approveAllCreditCard() throws AccountException, NotificationException {

        List<CreditCardQueue> creditCardQueueList=creditCardQueueRepository.findAll();
        if(creditCardQueueList.isEmpty()){
            throw new AccountException("NO ELEMENT PRESENT IN THE CREDIT CARD QUEUE");
        }
        for(CreditCardQueue creditCardQueueObject :creditCardQueueList){
            String creditCardNumber=creditCardQueueObject.getCreditCardNumber();
            creditCardService.updateActivationStatus(creditCardNumber,"ACTIVATED");
            creditCardQueueRepository.delete(creditCardQueueObject);
        }
        return "ACTIVATION COMPLETED";
    }

    @Override
    public String approveIndividualCreditCard(String creditCardNumber) throws AccountException,NotificationException{

        Optional<CreditCardQueue> creditCardQueue=creditCardQueueRepository.findByCreditCardNumber(creditCardNumber);
        if(creditCardQueue.isEmpty()){
            throw new AccountException("CREDIT CARD NUMBER NOT PRESENT");
        }
        if (creditCardQueue.isPresent()){
            creditCardService.updateActivationStatus(creditCardNumber,"ACTIVATED");
            creditCardQueueRepository.delete(creditCardQueue.get());
        }
        return "ACTIVATION COMPLETED";
    }
}
