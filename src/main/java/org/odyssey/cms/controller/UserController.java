package org.odyssey.cms.controller;

import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.service.CustomerService;
import org.odyssey.cms.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountException;

@RestController
public class UserController {
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private CustomerService customerService;

	@PostMapping("cms/merchant")
	public User createMerchant(@RequestBody User user)throws AccountException{
		return this.merchantService.createNewMerchant(user);
	}

	@PostMapping("cms/merchant/paymentrequest")
	public Boolean newPaymentRequest(@RequestBody PaymentRequest paymentRequest)throws AccountException{
		return this.merchantService.newRequest(paymentRequest.getMerchantId(),paymentRequest.getCustomerId(),paymentRequest.getRequestAmount());
	}

	@GetMapping("cms/customer/{customerId}")
	public String customerPaymentRequest(@PathVariable Integer customerId)throws AccountException{
		return this.customerService.paymentNotification(customerId);
	}

}
