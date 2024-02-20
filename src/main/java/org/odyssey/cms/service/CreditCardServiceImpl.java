package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.repository.CreditCardQueueRepository;
import org.odyssey.cms.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.odyssey.cms.entity.CreditCardQueue;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private static final Random random = new Random(System.currentTimeMillis());
    @Autowired
    private CreditCardRepository creditCardRepository;
    private CreditCardQueueRepository creditCardQueueRepository;

    @Override
    public CreditCard getCreditCardById(String cardNumber) throws AccountException{
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        if(optionalCreditCard.isEmpty()){
            throw new AccountException("Credit card not found.");
        }
        return optionalCreditCard.orElse(null);
    }

    @Override
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard createCreditCard(CreditCard creditCard) throws AccountException{
        String bin = "4";
        int length = 16;
        StringBuilder cardNumber = new StringBuilder(bin);
        int randomNumberLength = length - (bin.length() + 1);
        for (int i = 0; i < randomNumberLength; i++) {
            int digit = random.nextInt(10);
            cardNumber.append(digit);
        }
        int finalDigit = 5;
        cardNumber.append(finalDigit);
        String cardNum = cardNumber.toString();
        Random randomOne = new Random();
        int x = randomOne.nextInt(900) + 100;

        LocalDate today = LocalDate.now();
        LocalDate expDate = today.plusYears(5);

        creditCard.setCardNumber(cardNum);
        creditCard.setCvv(x);
        creditCard.setCreditBalance(100000.0);
        creditCard.setExpireDate(expDate);
        creditCard.setCreditLimit(100000.0);
        creditCard.setActivationStatus("Requested");

        CreditCardQueue creditCardQueue= new CreditCardQueue(0,creditCard.getCardNumber());
        this.creditCardQueueRepository.save(creditCardQueue);
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard updateExpireDate(String cardNumber, LocalDate newExpireDate) throws AccountException{
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        if(optionalCreditCard.isEmpty()){
            throw new AccountException("Credit card not found.");
        }
        CreditCard existingCreditCard = optionalCreditCard.get();
        existingCreditCard.setExpireDate(newExpireDate);
        return creditCardRepository.save(existingCreditCard);
    }

    @Override
    public CreditCard updateAmount(String cardNumber, Double newAmount) throws AccountException{
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        if(optionalCreditCard.isEmpty()){
            throw new AccountException("Credit card not found.");
        }
        CreditCard existingCreditCard = optionalCreditCard.get();
        existingCreditCard.setCreditBalance(newAmount);
        return creditCardRepository.save(existingCreditCard);
    }

    @Override
    public CreditCard updateActivationStatus(String cardNumber, String newActivationStatus) throws AccountException{
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        if(optionalCreditCard.isEmpty()){
            throw new AccountException("Credit card not found.");
        }
        CreditCard existingCreditCard = optionalCreditCard.get();
        existingCreditCard.setActivationStatus(newActivationStatus);
        return creditCardRepository.save(existingCreditCard);
    }

    @Override
    public String deleteByCreditCard(String cardNumber) throws AccountException {
        if(creditCardRepository.findByCardNumber(cardNumber).isEmpty()){
            throw new AccountException("Credit card not found.");
        }
        creditCardRepository.deleteByCardNumber(cardNumber);

        return cardNumber;
    }

    @Override
    public List<CreditCard> getAllAccounts() {
        return null;
    }
}
