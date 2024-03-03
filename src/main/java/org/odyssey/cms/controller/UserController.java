package org.odyssey.cms.controller;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.UserRepository;
import org.odyssey.cms.service.MerchantService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RequestMapping("user")
@RestController
public class UserController {
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserRepository userRepository;


	@PostMapping("/merchant")
	public User createMerchant(@RequestBody User user) throws AccountException,NotificationException{
		
		return this.merchantService.createNewMerchant(user);
	}

	@PostMapping("/merchant/paymentrequest")
	public Boolean newPaymentRequest(@RequestBody PaymentRequest paymentRequest)throws AccountException,NotificationException{
		return this.merchantService.newRequest(0,paymentRequest.getMerchantId(),paymentRequest.getCustomerId(),paymentRequest.getRequestAmount());
	}


	@PostMapping("/create")
	public User createnewUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws AccountException, NotificationException {
		return this.customerService.createUser(userRegistrationDTO);
	}

	@GetMapping("all")
	public List<User> getAllUser(){
		return this.customerService.getAllUser();
	}

	@GetMapping("all/{userId}")
	public User getUserByIds(@PathVariable("userId") Integer userId) throws AccountException{
		return this.customerService.getUserById(userId);
	}

	@PutMapping("update")
	public User updateUser(@RequestBody User user)throws AccountException,NotificationException{
		return this.customerService.updateUser(user);
	}

	@DeleteMapping("delete/{userId}")
	public String deleteAccountById(@PathVariable("userId") Integer userId)throws AccountException{
		return this.customerService.deleteUser(userId);
	}

	@GetMapping("cms/customer/requestInvoice")
	public Invoice generateCustomerInvoice(Transaction transaction, PaymentRequest paymentRequest) throws UserException {
		return customerService.generateCustomerInvoice(transaction,paymentRequest);
	}

	@GetMapping("cms/merchant/requestInvoice")
	public Invoice generateMerchantInvoice(Transaction transaction, PaymentRequest paymentRequest) throws UserException{
		return merchantService.generateMerchantInvoice(transaction,paymentRequest);
	}
}
