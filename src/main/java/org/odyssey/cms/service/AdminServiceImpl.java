package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.CreditCardQueue;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.CreditCardQueueException;
import org.odyssey.cms.repository.CreditCardQueueRepository;
import org.odyssey.cms.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CreditCardQueueRepository creditCardQueueRepository;
    @Autowired
    private CreditCardService creditCardService;

    @Override
    public String approveAllCreditCard() throws CreditCardQueueException, AccountException, CreditCardException,NotificationException {

        List<CreditCardQueue> creditCardQueueList=creditCardQueueRepository.findAll();
        if(creditCardQueueList.isEmpty()){
            throw new CreditCardQueueException("NO ELEMENT PRESENT IN THE CREDIT CARD QUEUE");
        }
        for(CreditCardQueue creditCardQueueObject :creditCardQueueList){
            Integer creditId=creditCardQueueObject.getCardId();
            creditCardService.updateActivationStatus(creditId,"ACTIVATED");
            creditCardQueueRepository.delete(creditCardQueueObject);
        }
        return "ACTIVATION COMPLETED";
    }

    @Override
<<<<public String approveIndividualCreditCard(Integer cardId) throws CreditCardQueueException, AccountException, CreditCardException,NotificationException {
        Optional<CreditCardQueue> creditCardQueue=creditCardQueueRepository.findByCardId(cardId);

        if(creditCardQueue.isEmpty()){
            throw new CreditCardQueueException("CREDIT CARD NUMBER NOT PRESENT");
        }
        if (creditCardQueue.isPresent()){
            creditCardService.updateActivationStatus(cardId,"ACTIVATED");
            creditCardQueueRepository.delete(creditCardQueue.get());
        }
        return "ACTIVATION COMPLETED";
    }
}
