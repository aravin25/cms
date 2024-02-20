package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {
	@Autowired
	private CreditCardRepository creditCardRepository;

	@Override
	public List<CreditCard> getCreditCards() {
		return this.creditCardRepository.findAll();
	}
}
