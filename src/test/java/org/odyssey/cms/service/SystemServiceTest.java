package org.odyssey.cms.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.repository.CreditCardRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SystemServiceTest {
	@Mock
	private CreditCardServiceImpl creditCardService;
	@Mock
	private CreditCardRepository creditCardRepository;
	@InjectMocks
	private SystemServiceImpl systemService;

	List<CreditCard> creditCardList1;
	Integer activeCardsInList1;

	@BeforeEach
	void setup() {
		creditCardList1 = new ArrayList<>();
		creditCardList1.add(setupCreditCard(1, 1000.0, "ACTIVATED"));
		creditCardList1.add(setupCreditCard(2, 2000.0, "REQUESTED"));
		creditCardList1.add(setupCreditCard(3, 3000.0, "ACTIVATED"));
		activeCardsInList1 = 2;

	}

	CreditCard setupCreditCard(int id, double balance, String activation) {
		CreditCard creditCard = new CreditCard();
		creditCard.setCardId(id);
		creditCard.setCreditBalance(balance);
		creditCard.setActivationStatus(activation);
		Account account = new Account();
		account.setAccountId(id);
		creditCard.setAccount(account);

		return creditCard;
	}

	@Test
	void batchProcessWhenCardsExistTest() {
		when(this.creditCardService.getAllCreditCards()).thenReturn(creditCardList1);
		this.systemService.batchProcess();
		verify(creditCardRepository, times(activeCardsInList1)).save(any());
	}
}
