package org.odyssey.cms.service;

import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService  {
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;

	@Override
	public String paymentNotification(Integer customerId) throws AccountException {
		Optional<PaymentRequest> customerPaymentRequest= this.paymentRequestRepository.findByCustomerId(customerId);
		PaymentRequest customerRequest=customerPaymentRequest.get();
		if (customerPaymentRequest.isEmpty()) {
			return "customer don't have any PaymentRequest";
		}
		else{
			return ("customer have request form"+customerRequest.getMerchantId()+"\n amount: "+customerRequest.getRequestAmount());
		}
	}
}
