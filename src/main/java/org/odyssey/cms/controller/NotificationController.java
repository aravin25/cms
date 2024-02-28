package org.odyssey.cms.controller;

import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("creditcard")
@RestController
public class NotificationController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("PaymentRequest/{ID}")
	public String getPaymentNotification(@PathVariable("ID") Integer customerID)throws AccountException {
		return customerService.paymentNotification(customerID);
	}
}
