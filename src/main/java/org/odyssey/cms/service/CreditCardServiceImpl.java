package org.odyssey.cms.service;

import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.CreditCardQueueRepository;
import org.odyssey.cms.repository.CreditCardRepository;
import org.odyssey.cms.repository.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CreditCardQueueRepository creditCardQueueRepository;

    @Override
    public CreditCard getCreditCardById(String cardNumber) throws CreditCardException {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        if(optionalCreditCard.isEmpty()){
            throw new CreditCardException("Credit card not found.");
        }
        return optionalCreditCard.orElse(null);
    }


    @Override
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard createCreditCard(CreditCard creditCard) {
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
        int cvv = randomOne.nextInt(900) + 100;
        int pin = randomOne.nextInt(9000) + 1000;

        LocalDate today = LocalDate.now();
        LocalDate expDate = today.plusYears(5);

        creditCard.setCardNumber(cardNum);
        creditCard.setCvv(cvv);
        creditCard.setCreditBalance(50000.0);
        creditCard.setExpireDate(expDate);
        creditCard.setCreditLimit(100000.0);
        creditCard.setActivationStatus("REQUESTED");

        creditCard.setPinNumber(pin);
        CreditCardQueue creditCardQueue= new CreditCardQueue(0,creditCard.getCardNumber());
        this.creditCardQueueRepository.save(creditCardQueue);
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard updateExpireDate(String cardNumber, LocalDate newExpireDate) throws CreditCardException {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        if(optionalCreditCard.isEmpty()){
            throw new CreditCardException("Credit card not found.");
        }
        CreditCard existingCreditCard = optionalCreditCard.get();
        existingCreditCard.setExpireDate(newExpireDate);
        return creditCardRepository.save(existingCreditCard);
    }

    @Override
    public CreditCard updateAmount(String cardNumber, Double newAmount) throws CreditCardException {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        if(optionalCreditCard.isEmpty()){
            throw new CreditCardException("Credit card not found.");
        }
        CreditCard existingCreditCard = optionalCreditCard.get();
        existingCreditCard.setCreditBalance(newAmount);
        return creditCardRepository.save(existingCreditCard);
    }

    @Override
    public CreditCard updateActivationStatus(String cardNumber, String newActivationStatus) throws CreditCardException {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        if(optionalCreditCard.isEmpty()){
            throw new CreditCardException("Credit card not found.");
        }
        CreditCard existingCreditCard = optionalCreditCard.get();
        existingCreditCard.setActivationStatus(newActivationStatus);
        return creditCardRepository.save(existingCreditCard);
    }

    @Override
    public String deleteByCreditCard(String cardNumber) throws CreditCardException {
        if(creditCardRepository.findByCardNumber(cardNumber).isEmpty()){
            throw new CreditCardException("Credit card not found.");
        }
        creditCardRepository.deleteByCardNumber(cardNumber);

        return cardNumber;
    }

    @Override
    public List<CreditCard> getAllAccounts() {
        return null;
    }

    @Override
    public CreditCard getCreditCardByUserId(Integer userId) throws UserException, AccountException, CreditCardException {
        Optional<User> optionalUser = this.userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserException("User doesn't exist. Can't fetch credit card.");
        }
        User user = optionalUser.get();
        Account account = user.getAccount();

        if (account == null) {
            throw new AccountException("Account doesn't exist for this user. Can't fetch credit card.");
        }
        CreditCard creditCard = account.getCreditCard();

        if (creditCard == null) {
            throw new CreditCardException("Credit card doesn't exist for this account. Can't fetch credit card.");
        }

        return creditCard;
    }
}
