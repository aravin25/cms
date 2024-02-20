package org.odyssey.cms.controller;

import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.service.MerchantService;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
		return this.merchantService.newRequest(0,paymentRequest.getMerchantId(),paymentRequest.getCustomerId(),paymentRequest.getRequestAmount());
	}

	@GetMapping("cms/customer/{customerId}")
	public String customerPaymentRequest(@PathVariable Integer customerId)throws AccountException{
		return this.customerService.paymentNotification(customerId);
	}

	@PostMapping("cms/createUser")
	public User createnewUser(@RequestBody User user) throws AccountException {
		return this.customerService.createUser(user);
	}

	@GetMapping("getAllUser")
	public List<User> getAllUser(){
		return this.customerService.getAllUser();
	}

	@GetMapping("allUser/{userId}")
	public User getUserByIds(@PathVariable("userId") Integer userId) throws AccountException{
		return this.customerService.getUserById(userId);
	}

	@PutMapping("updateUser")
	public User updateAccount(@RequestBody User user)throws AccountException{
		return this.customerService.updateUser(user);
	}

	@DeleteMapping("deleteUser/{userId}")
	public String deleteAccountById(@PathVariable("userId") Integer userId)throws AccountException{
		return this.customerService.deleteUser(userId);
	}
}
