package org.odyssey.cms.service;

import org.odyssey.cms.entity.CreditCard;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CreditCardService {
	List<CreditCard> getCreditCards();
}
