package org.odyssey.cms.controller;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.RequestInvoiceDTO;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.dto.UserUpdateDTO;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.UserRepository;
import org.odyssey.cms.service.CustomerService;
import org.odyssey.cms.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
	public User createMerchant(@RequestBody UserRegistrationDTO userRegistrationDTO) throws AccountException, UserException {
		
		return this.merchantService.createNewMerchant(userRegistrationDTO);
	}

	@PostMapping("/merchant/paymentRequest")
	public Boolean newPaymentRequest(@RequestBody PaymentRequest paymentRequest)throws AccountException{
		return this.merchantService.newRequest(0,paymentRequest.getMerchantId(),paymentRequest.getCustomerId(),paymentRequest.getRequestAmount());
	}

	@PostMapping("create")
	public User createnewUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws AccountException, UserException {
		return this.customerService.createUser(userRegistrationDTO);
	}

	@GetMapping("all")
	public List<User> getAllUser(){
		return this.customerService.getAllUser();
	}

	@GetMapping("all/{userId}")
	public User getUserByIds(@PathVariable("userId") Integer userId) throws AccountException, UserException {
		return this.customerService.getUserById(userId);
	}

	@PutMapping("update")
	public User updateUser(@RequestBody UserUpdateDTO userUpdateDTO) throws AccountException, UserException {
		return this.customerService.updateUser(userUpdateDTO);
	}

	@DeleteMapping("delete/{userId}")
	public String deleteAccountById(@PathVariable("userId") Integer userId) throws AccountException, UserException {
		return this.customerService.deleteUser(userId);
	}

	@GetMapping("customer/requestInvoice")
	public Invoice generateCustomerInvoice(@RequestBody RequestInvoiceDTO requestInvoiceDTO) throws UserException, PaymentRequestException {
		return customerService.generateCustomerInvoice(requestInvoiceDTO);
	}

	@GetMapping("merchant/requestInvoice")
	public Invoice generateMerchantInvoice(@RequestBody RequestInvoiceDTO requestInvoiceDTO) throws UserException, PaymentRequestException {
		return merchantService.generateMerchantInvoice(requestInvoiceDTO);
	}

	@GetMapping("paymentRequests")
	public List<PaymentRequest> getAllPaymentRequests(@RequestParam Integer userId) throws UserException {
		return this.customerService.getAllPaymentRequests(userId);
	}

}
