package org.odyssey.cms.service;

import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.springframework.stereotype.Service;


@Service
public interface MerchantService {
	User createNewMerchant(User newMerchant)throws AccountException;
	Boolean newRequest(Integer paymentRequestId, Integer merchantId, Integer customerId, Double amount)throws AccountException;
}
