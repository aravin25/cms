package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
	@Autowired
	private CreditCardService creditCardService;
	@Autowired
	private TransactionService transactionService;

	@Scheduled(fixedRate = 10000)
	@Override
	public void batchProcess() {
		List<CreditCard> creditCardList = this.creditCardService.getAllCreditCards();

		for (CreditCard creditCard : creditCardList) {
			Double creditBalance = creditCard.getCreditBalance();
			Integer accountId = creditCard.getAccount().getAccountId();

			if (creditBalance != 0) {
				creditCard.addInterest();
			}
		}
	}
}
