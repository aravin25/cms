package org.odyssey.cms.service;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.repository.AccountRepository;
import org.odyssey.cms.repository.CreditCardRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private CreditCardRepository creditCardRepository;
	@Mock
	private NotificationService notificationService;

	@BeforeEach
	void setup() {
		Account account = new Account();
		account.setAccountId(1);
		account.setBalance(1000.0);

		CreditCard creditCard = new CreditCard();
		creditCard.setCardId(1);
		creditCard.setActivationStatus("ACTIVATED");
		creditCard.setCreditBalance(500.0);
		creditCard.setAccount(account);
		account.setCreditCard(creditCard);
	}

	@Test
	void creditBalancePaymentAccountDoesntExist() {
		when(accountRepository.findById(any())).thenReturn(Optional.empty());
	}


}
