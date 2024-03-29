package org.odyssey.cms.service;

import org.odyssey.cms.dto.TransactionDTO;
import org.odyssey.cms.entity.LastPayment;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.TransactionException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.LastPaymentRepository;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.repository.AccountRepository;
import org.odyssey.cms.repository.CreditCardRepository;
import org.odyssey.cms.repository.TransactionRepository;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	PaymentRequestRepository paymentRequestRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private LastPaymentRepository lastPaymentRepository;

	@Autowired
	private AccountService accountService;
 
	@Autowired
	private CreditCardService creditCardService;

	String expectedPin = "xyz@123";

	@Override
	public Transaction createTransaction(Transaction newTransaction) throws TransactionException   {
		Optional<Transaction> optionalTransaction = this.transactionRepository.findById(newTransaction.getTransactionID());
		if (optionalTransaction.isPresent()) {
			throw new TransactionException("Transaction already exists!");
		}
		newTransaction.setTransactionID(0);
		newTransaction.setTransactionDateTime(LocalDateTime.now());
    	notificationService.saveNotification(newTransaction.getCreditCard().getAccount().getUser().getUserId(),"Transaction","Transaction created");
		return this.transactionRepository.save(newTransaction);
	}

	@Override
	public Transaction getTransactionById(Integer transactionId) throws TransactionException {
		Optional<Transaction> optionalTransaction = this.transactionRepository.findById(transactionId);
		if (optionalTransaction.isEmpty()) {
			throw new TransactionException("Transaction doesn't exists!");
		}
		return optionalTransaction.get();
	}

	@Override
	public List<Transaction> getTransactionByUserId(Integer userId) throws UserException, AccountException, TransactionException, CreditCardException
	{
		Optional<User> optionalUser = this.userRepository.findById(userId);
		if(optionalUser.isEmpty()){
			throw new UserException("User does not exist.");
		}
		User user = optionalUser.get();
		Account account = user.getAccount();
		if(account == null){
			throw new AccountException("Account does not exist for the user.");
		}
		List<CreditCard> creditCards = account.getCreditCards();
		if(creditCards == null){
			throw new CreditCardException("Credit card does not exist for this account");
		}
		List<Transaction> transactionList = creditCards.stream().flatMap(creditCard -> creditCard.getTransactionList().stream()).collect(Collectors.toList());
		if (transactionList.isEmpty()){
			throw new TransactionException("No transactions made by the user.");
		}
		return transactionList;
	}

	@Override
	public List<Transaction> getAllTransactions() {
		return this.transactionRepository.findAll();
	}

	@Override
	public void creditBalancePayment(Integer accountId, String password, Double amount, String cardNumber) throws AccountException, CreditCardException  {
		Optional<Account> optionalAccount = this.accountRepository.findById(accountId);

		if (optionalAccount.isEmpty()) {
			throw new AccountException("Account doesn't exist: " + accountId);
		}

		Account account = optionalAccount.get();

		if (amount != null && account.getBalance() < amount) {
			throw new AccountException("Account doesn't have sufficient balance");
		}
		if (!account.getPassword().equals(password)) {
			throw new AccountException("Passwords don't match. Can't pay credit balance");
		}

		CreditCard creditCard = creditCardService.getCreditCardById(cardNumber);

		if (creditCard == null) {
			throw new CreditCardException("Credit card should exist to pay credit balance");
		}
		if (!creditCard.getActivationStatus().equals("ACTIVATED")) {
			throw new CreditCardException("Credit card is not activated yet");
		}

		if (amount == null) {
			account.setBalance(account.getBalance() - creditCard.getCreditBalance());
			creditCard.setCreditBalance(0.0);
		} else if (amount > creditCard.getCreditBalance()) {
			throw new AccountException("User is paying more than the outstanding credit balance");
		} else {
			creditCard.setCreditBalance(creditCard.getCreditBalance() - amount);
			account.setBalance(account.getBalance() - amount);
		}

		this.creditCardRepository.save(creditCard);
    	this.notificationService.saveNotification(optionalAccount.get().getUser().getUserId(),"Transaction","Payment Successfully completed");
		this.accountRepository.save(account);
		accountService.createLasteDate(new LastPayment(0,accountId,amount, LocalDate.now()));
	}

	@Override
	public boolean processTransaction(TransactionDTO transactionDTO, String cardNumber) throws PaymentRequestException, AccountException, CreditCardException, UserException, TransactionException {
		Integer paymentRequestId = transactionDTO.getPaymentRequestId();
		Optional<PaymentRequest> optionalPaymentRequest = this.paymentRequestRepository.findById(paymentRequestId);

		if (optionalPaymentRequest.isEmpty()) {
			throw new PaymentRequestException("Payment Request doesn't exist. Check your payment request id.");
		}

		PaymentRequest paymentRequest = optionalPaymentRequest.get();

		String topic = paymentRequest.getTopic();
		List<CreditCard> creditCards = this.creditCardService.getCreditCardsByUserId(paymentRequest.getCustomerId());
		CreditCard creditCard = creditCards.stream().filter(card -> Objects.equals(card.getCardNumber(), cardNumber)).findFirst().get();

		if(creditCard.getAccount().getUser().getLogin()==false){
			throw new UserException("Not Login");
		}
		if (!creditCard.getPinNumber().equals(transactionDTO.getInputPin())) {
			throw new CreditCardException("Provided PIN doesn't match the credit card.");
		}

		Optional<User> optionalMerchant = this.userRepository.findById(paymentRequest.getMerchantId());

		if (optionalMerchant.isEmpty()) {
			throw new UserException("Merchant provided in the payment request doesn't exist.");
		}
		User merchant = optionalMerchant.get();
		Account merchantAccount = merchant.getAccount();
		String merchantName =merchant.getName();

		if (merchantAccount == null) {
			throw new AccountException("Account doesn't exist for this merchant");
		}

		Double transactionAmount = paymentRequest.getRequestAmount();
		//System.out.println(transactionAmount);

		if(Objects.equals(creditCard.getActivationStatus(), "ACTIVATED")) {
			if (transactionAmount <= 0) {
				throw new TransactionException("Transaction amount cannot be null!");
			} else if (transactionAmount > creditCard.getCreditLimit() - creditCard.getCreditBalance()) {
				throw new TransactionException("Transaction amount is exceeding the credit limit.");
			}  else {
				creditCard.setCreditBalance(creditCard.getCreditBalance() + transactionAmount);
				merchantAccount.setBalance(merchantAccount.getBalance() + transactionAmount);
				System.out.println("The transaction was successful.");

				Transaction transaction = new Transaction();
				transaction.setTransactionID(0);
				transaction.setAmount(transactionAmount);
				transaction.setCreditCard(creditCard);
				transaction.setTopic(topic);
				transaction.setMerchant(merchantName);
				transaction.setPaymentRequestId(transactionDTO.getPaymentRequestId());
				this.createTransaction(transaction);
				paymentRequest.setStatus("COMPLETED");
//				this.paymentRequestRepository.deleteById(paymentRequestId);

				this.accountRepository.save(merchantAccount);
				this.creditCardRepository.save(creditCard);
			}
		} else {
			throw new TransactionException("Card is not activated yet!");
		}
    //notificationService.saveNotification(customerId,"Transaction","Processing");
		return true;
	}
}

