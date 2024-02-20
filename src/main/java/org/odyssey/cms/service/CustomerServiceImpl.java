package org.odyssey.cms.service;

<<<<<<< Updated upstream
import lombok.Setter;
import org.odyssey.cms.entity.User;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	User user;
	public void hi(){
		System.out.println(user.getUserId());
=======
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService  {
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;

	@Override
	public String paymentNotification(Integer customerId) throws AccountException {
		Optional<PaymentRequest> customerPaymentRequest = this.paymentRequestRepository.findByCustomerId(customerId);
		PaymentRequest customerRequest=customerPaymentRequest.get();
		if (customerPaymentRequest.isEmpty()) {
			return "customer don't have any PaymentRequest";
		}
		else{
			return ("payment Request Id: "+customerRequest.getPaymentRequestId()+"\ncustomer have request form"+customerRequest.getMerchantId()+"\namount: "+customerRequest.getRequestAmount());
		}
>>>>>>> Stashed changes
	}
}
