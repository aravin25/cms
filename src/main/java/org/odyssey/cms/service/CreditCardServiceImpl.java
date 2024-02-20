package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {

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
    public CreditCard updateAmount(Integer cardNumber, Integer newAmount) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(cardNumber);
        if (optionalCreditCard.isPresent()) {
            CreditCard existingCreditCard = optionalCreditCard.get();
            existingCreditCard.setAmount(newAmount);
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
