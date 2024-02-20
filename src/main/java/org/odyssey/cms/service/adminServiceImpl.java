package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.CreditCardQueue;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.repository.CreditCardQueueRepository;
import org.odyssey.cms.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class adminServiceImpl implements AdminService{
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CreditCardQueueRepository creditCardQueueRepository;
    @Autowired
    private CreditCardService creditCardService;
    @Override
    public String approveCreditCard() throws AccountException {

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
}
