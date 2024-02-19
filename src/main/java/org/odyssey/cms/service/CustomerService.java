package org.odyssey.cms.service;

import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;

@Service
public interface CustomerService {
	String paymentNotification(Integer customerId)throws AccountException;
}
