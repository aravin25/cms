package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private static final Random random = new Random(System.currentTimeMillis());
    @Autowired
    private CreditCardRepository creditCardRepository;

    @Override
    public CreditCard getCreditCardById(Integer cardNumber) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(cardNumber);
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
        int x = randomOne.nextInt(900) + 100;

        LocalDate today = LocalDate.now();
        LocalDate expDate = today.plusYears(5);

        creditCard.setCardNumber(cardNum);
        creditCard.setCvv(x);
        creditCard.setCreditBalance(100000.0);
        creditCard.setExpireDate(expDate);
        creditCard.setCreditLimit(100000.0);
        creditCard.setActivationStatus("Requested");
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard updateExpireDate(Integer cardNumber, LocalDate newExpireDate) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(cardNumber);
        if (optionalCreditCard.isPresent()) {
            CreditCard existingCreditCard = optionalCreditCard.get();
            existingCreditCard.setExpireDate(newExpireDate);
            return creditCardRepository.save(existingCreditCard);
        } else {
            return null;
        }
    }

    @Override
    public CreditCard updateAmount(Integer cardNumber, Double newAmount) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(cardNumber);
        if (optionalCreditCard.isPresent()) {
            CreditCard existingCreditCard = optionalCreditCard.get();
            existingCreditCard.setCreditBalance(newAmount);
            return creditCardRepository.save(existingCreditCard);
        } else {
            return null;
        }
    }

    @Override
    public CreditCard updateActivationStatus(Integer cardNumber, String newActivationStatus) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(cardNumber);
        if (optionalCreditCard.isPresent()) {
            CreditCard existingCreditCard = optionalCreditCard.get();
            existingCreditCard.setActivationStatus(newActivationStatus);
            return creditCardRepository.save(existingCreditCard);
        } else {
            return null;
        }
    }

    @Override
    public void deleteCreditCard(Integer cardNumber) {
        creditCardRepository.deleteById(cardNumber);
    }
}
