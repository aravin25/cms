package org.odyssey.cms.service;

import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.AccountRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private static final Random random = new Random(System.currentTimeMillis());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CreditCardQueueRepository creditCardQueueRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JavaMailSender emailSender;

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

    public static String generateCardNumber(String cardVendorType) throws CreditCardException {
        StringBuilder cardNumber = new StringBuilder();
        switch (cardVendorType) {
            case "MasterCard": // Starts with 51-55, 2221-2720; Length 16
                if (random.nextBoolean()) {
                    cardNumber.append(random.nextInt(55 - 51 + 1) + 51);
                } else {
                    cardNumber.append(random.nextInt(2720 - 2221 + 1) + 2221);
                }
                break;

            case "Visa": // Starts with 4; Length 16
                cardNumber.append("4");
                break;

            case "AmEx": // Starts with 34 or 37; Length 15
                cardNumber.append(random.nextBoolean() ? "34" : "37");
                break;

            case "Discover": // Starts with 6011, 644-649, 65; Length 16
                if (random.nextBoolean()) {
                    cardNumber.append("6011");
                } else {
                    cardNumber.append(random.nextBoolean() ? (random.nextInt(6) + 644) : 65);
                }
                break;

            default:
                throw new CreditCardException("Unsupported card vendor type");
        }

        int length = (cardVendorType.equals("AmEx")) ? 14 : 15;
        while (cardNumber.length() < length) {
            cardNumber.append(random.nextInt(10));
        }
        cardNumber.append(getLuhnChecksumDigit(cardNumber.toString()));

        return cardNumber.toString();
    }

    private static int getLuhnChecksumDigit(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum * 9) % 10;
    }

    @Override
    public CreditCard createCreditCard(CreditCard creditCard) throws CreditCardException {
        String cardNum = generateCardNumber(creditCard.getVendor());
        Random randomOne = new Random();
        int cvv = randomOne.nextInt(900) + 100;
        int pin = randomOne.nextInt(9000) + 1000;

        LocalDate today = LocalDate.now();
        LocalDate expDate = today.plusYears(5);

        creditCard.setCardNumber(cardNum);
        creditCard.setCvv(cvv);
        creditCard.setCreditBalance(0.0);
        creditCard.setExpireDate(expDate);
        creditCard.setCreditLimit(100000.0);
        creditCard.setActivationStatus("REQUESTED");
        creditCard.setPinNumber(pin);

        CreditCardQueue creditCardQueue= new CreditCardQueue(0,creditCard.getCardNumber());
        this.creditCardQueueRepository.save(creditCardQueue);
//        notificationService.saveNotification(creditCard.getAccount().getUser().getUserId(),"CreditCard","CreditCard created");
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard createCreditCardByAccountId(CreditCard creditCard, Integer accountId) throws CreditCardException, AccountException {
        String cardNum = generateCardNumber(creditCard.getVendor());
        Random randomOne = new Random();
        int cvv = randomOne.nextInt(900) + 100;
        int pin = randomOne.nextInt(9000) + 1000;

        LocalDate today = LocalDate.now();
        LocalDate expDate = today.plusYears(5);

        creditCard.setCardNumber(cardNum);
        creditCard.setCvv(cvv);
        creditCard.setCreditBalance(0.0);
        creditCard.setExpireDate(expDate);
        creditCard.setCreditLimit(100000.0);
        creditCard.setActivationStatus("REQUESTED");
        creditCard.setPinNumber(pin);
        creditCard.setVendor(creditCard.getVendor());

        Optional<Account> OptAccount = this.accountRepository.findById(accountId);
        if(OptAccount.isEmpty()){
            throw new AccountException("Account is not present");
        }

        Account account = OptAccount.get();
        creditCard.setBank(account.getBankType());
        creditCard.setAccount(account);

        CreditCardQueue creditCardQueue= new CreditCardQueue(0,creditCard.getCardNumber());
        this.creditCardQueueRepository.save(creditCardQueue);
//        notificationService.saveNotification(creditCard.getAccount().getUser().getUserId(),"CreditCard","CreditCard created");
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
        notificationService.saveNotification(existingCreditCard.getAccount().getUser().getUserId(),"CreditCard","CreditCard ExpireDate Updated");
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
        notificationService.saveNotification(existingCreditCard.getAccount().getUser().getUserId(),"CreditCard","Amount Updated");
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
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findByCardNumber(cardNumber);
        notificationService.saveNotification(optionalCreditCard.get().getAccount().getUser().getUserId(),"CreditCard","CreditCard created");
        return cardNumber;
    }

    @Override
    public List<CreditCard> getAllAccounts() {
        return null;
    }

    @Override
    public String pinGeneratation(Integer firstHalf,Integer cardId) throws CreditCardException {
        if(firstHalf>99){
            throw new CreditCardException("first half should be two digit");
        }
        Optional<CreditCard> creditCardOptional= creditCardRepository.findById(cardId);
        if(creditCardOptional.isEmpty()){
            throw new CreditCardException("Card Not Created");
        }
        CreditCard creditCard=creditCardOptional.get();
        Integer prevPin= creditCard.getPinNumber();
        Integer secfHalf=prevPin%10;
        prevPin=prevPin/10;
        secfHalf=10*(prevPin%10)+secfHalf;
        creditCard.setPinNumber((100*firstHalf)+secfHalf);
        creditCardRepository.save(creditCard);

        String email=creditCard.getAccount().getUser().getEmail();
        String creditNo=creditCard.getCardNumber();
        String userName=creditCard.getAccount().getUser().getName();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("creditcardmanagmentsystem@gmail.com");
        message.setTo(email);
        message.setSubject("Pin Change For CreditCard:"+creditNo);
        message.setText("Hi"+userName+"!\n You Have Currently Requested a pin Change For Creditcard: "+creditNo+
                " At "+LocalDate.now()+"\n Now User Pin is:"+creditCard.getPinNumber()+"\n\n Thank You,\nCMS TEAM");
        emailSender.send(message);
        return ("Pin Created Successfully \n pin: "+creditCard.getPinNumber());
    }

    @Override
    public List<CreditCard> getCreditCardsByUserId(Integer userId) throws UserException, AccountException, CreditCardException {
        Optional<User> optionalUser = this.userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserException("User doesn't exist. Can't fetch credit card.");
        }
        User user = optionalUser.get();
        Account account = user.getAccount();

        if (account == null) {
            throw new AccountException("Account doesn't exist for this user. Can't fetch credit card.");
        }
        List<CreditCard> creditCards = account.getCreditCards();

        if (creditCards == null) {
            throw new CreditCardException("Credit card doesn't exist for this account. Can't fetch credit card.");
        }

        return creditCards;
    }
}
