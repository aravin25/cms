package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCardQueue;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.CreditCardQueueException;
import org.odyssey.cms.exception.UserException;
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
    public String approveAllCreditCard() throws CreditCardQueueException, AccountException, CreditCardException {

        List<CreditCardQueue> creditCardQueueList=creditCardQueueRepository.findAll();
        if(creditCardQueueList.isEmpty()){
            throw new CreditCardQueueException("NO ELEMENT PRESENT IN THE CREDIT CARD QUEUE");
        }
        for(CreditCardQueue creditCardQueueObject :creditCardQueueList){
            String creditCardNumber=creditCardQueueObject.getCreditCardNumber();
            creditCardService.updateActivationStatus(creditCardNumber,"ACTIVATED");
            creditCardQueueRepository.delete(creditCardQueueObject);
        }
        return "ACTIVATION COMPLETED";
    }

    @Override
    public String approveIndividualCreditCard(String creditCardNumber) throws CreditCardQueueException, AccountException, CreditCardException {
        Optional<CreditCardQueue> creditCardQueue=creditCardQueueRepository.findByCreditCardNumber(creditCardNumber);
        if(creditCardQueue.isEmpty()){
            throw new CreditCardQueueException("CREDIT CARD NUMBER NOT PRESENT");
        }
		creditCardService.updateActivationStatus(creditCardNumber, "ACTIVATED");
		creditCardQueueRepository.delete(creditCardQueue.get());
		return "ACTIVATION COMPLETED";
    }
    @Override
    public List<CreditCardQueue> getAllCreditCardQueue(){
        return this.creditCardQueueRepository.findAll();
    }

    @Override
    public String logInAdmin(String email, String password) throws UserException
    {
        if(!email.equals("admin@gmail.com")){
            throw new UserException("Enter Valid EmailID");
        }
        if (!password.equals("Admin@123")){
            throw new UserException("Enter Valid Password");
        }
        return "Login Successful";
    }
}
