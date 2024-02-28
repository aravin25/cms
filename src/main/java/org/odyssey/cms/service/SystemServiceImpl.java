package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private CreditCardService creditCardService;
	@Autowired
	private TransactionService transactionService;


	@Scheduled(cron = "${scheduler.balance-batch-process.cron}")
	@Override
	public void batchProcess() {
		List<CreditCard> creditCardList = this.creditCardService.getAllCreditCards();

		for (CreditCard creditCard : creditCardList) {
			Double creditBalance = creditCard.getCreditBalance();
			Integer accountId = creditCard.getAccount().getAccountId();

			if (!creditCard.getActivationStatus().equals("ACTIVATED")) {
				continue;
			}

			if (creditBalance != 0) {
				creditCard.addInterest();
			}

			this.creditCardRepository.save(creditCard);
		}
	}
}
