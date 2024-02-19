package org.odyssey.cms.service;

import org.odyssey.cms.entity.User;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;

@Service
public interface MerchantService {
	User createNewMerchant(User newMerchant)throws AccountException;
	Boolean newRequest(Integer merchantId,Integer customerId,Double amount)throws AccountException;
}
