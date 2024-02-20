package org.odyssey.cms.service;

import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.repository.AccountRepository;
import org.odyssey.cms.repository.CreditCardRepository;
import org.odyssey.cms.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {
  @Autowired
  PaymentRequestRepository paymentRequestRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CreditCardRepository creditCardRepository;
  
  String expectedPin = "xyz@123";
  
	@Override
	public Transaction createTransaction(Transaction newTransaction) throws AccountException {
		Optional<Transaction> transaction = this.transactionRepository.findById(newTransaction.getTransactionID());
		if (transaction.isPresent()) {
			throw new AccountException("transaction already exists!");
		}
		return this.transactionRepository.save(newTransaction);
	}

	@Override
	public Transaction getTransactionById(Integer transactionId) throws AccountException {
		Optional<Transaction> transaction = this.transactionRepository.findById(transactionId);
		if (!transaction.isPresent()) {
			throw new AccountException("transaction not exists!");
		}
		return this.transactionRepository.findById(transactionId).get();
	}

	@Override
	public List<Transaction> getAllTransactions() {
		return this.transactionRepository.findAll();
	}

	@Override
	public List<Transaction> getTransactionsFromThisCycle() {
		return this.getAllTransactions();
	}

	@Override
	public String creditBalancePayment(Integer accountId, String password, Double amount) throws AccountException, CreditCardException {
		Optional<Account> optionalAccount = this.accountRepository.findById(accountId);

		if (optionalAccount.isEmpty()) {
			throw new AccountException("Account doesn't exists: " + accountId);
		}

		Account account = optionalAccount.get();

		if (account.getBalance() < amount) {
			throw new AccountException("Account doesn't have sufficient balance");
		}
		if (!account.getPassword().equals(password)) {
			throw new AccountException("Passwords don't match. Can't pay credit balance");
		}

		CreditCard creditCard = account.getCreditCard();

		if (creditCard == null) {
			throw new CreditCardException("Credit card should exist to pay credit balance");
		}

		String paymentStatus = "";

		if (amount > creditCard.getCreditBalance()) {
			throw new AccountException("User is paying more than the outstanding credit balance");
		} else {
			creditCard.setCreditBalance(creditCard.getCreditBalance() - amount);
		}

		this.creditCardRepository.save(creditCard);
		return paymentStatus;
	}
  
  @Override
  public boolean authPin(String inputPin) {
    return inputPin.equals(expectedPin);
  }
  
  @Override
  public boolean processTransaction(Integer customerId, CreditCard creditCard) {
    Double transactionAmount = paymentRequestRepository.findById(customerId).get().getRequestAmount();
    if(Objects.equals(creditCard.getActivationStatus(), "Completed")) {
        if (transactionAmount <= 0) {
//            System.out.println("Not possible");
          return false;
        } else if (transactionAmount > creditCard.getCreditLimit() - creditCard.getCreditBalance()) {
//        System.out.println("Transaction amount is exceeding the credit limit.");
          return false;
        } else {
          creditCard.setCreditBalance(creditCard.getCreditBalance() + transactionAmount);
          System.out.println("The transaction was successful.");
          paymentRequestRepository.deleteById(customerId);
        }
      }
     return true;
  }
}

