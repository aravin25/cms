package org.odyssey.cms.controller;

import jakarta.validation.Valid;
import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.PaymentRequestDTO;
import org.odyssey.cms.dto.RequestInvoiceDTO;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.dto.UserUpdateDTO;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.TransactionException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.AccountRepository;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.TransactionRepository;
import org.odyssey.cms.repository.UserRepository;
import org.odyssey.cms.service.MerchantService;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.service.CustomerService;
import org.odyssey.cms.service.UserLoginService;
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
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserLoginService userLoginService;


	@PostMapping("/merchant")
	public User createMerchant(@RequestBody UserRegistrationDTO userRegistrationDTO) throws AccountException, UserException, CreditCardException {
		return this.merchantService.createNewMerchant(userRegistrationDTO);
	}

	@PostMapping("/merchant/paymentRequest")
	public Boolean newPaymentRequest(@RequestBody PaymentRequest paymentRequest)throws AccountException{
		return this.merchantService.newRequest(0,paymentRequest.getMerchantId(),paymentRequest.getCustomerId(),paymentRequest.getRequestAmount(),paymentRequest.getTopic());
	}

	@PostMapping("/customer")
	public User createnewUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws AccountException, UserException, CreditCardException {
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
	public User updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) throws AccountException, UserException {
		return this.customerService.updateUser(userUpdateDTO);
	}

	@DeleteMapping("delete/{userId}")
	public String deleteAccountById(@PathVariable("userId") Integer userId) throws AccountException, UserException {
		return this.customerService.deleteUser(userId);
	}

	@PostMapping("customer/requestInvoice")
	public Invoice generateCustomerInvoice(@RequestBody RequestInvoiceDTO requestInvoiceDTO) throws UserException, PaymentRequestException, TransactionException, AccountException
	{
		return customerService.generateCustomerInvoice(requestInvoiceDTO);
	}

	@PostMapping("merchant/requestInvoice")
	public Invoice generateMerchantInvoice(@RequestBody RequestInvoiceDTO requestInvoiceDTO) throws UserException, PaymentRequestException, TransactionException, AccountException
	{
		return merchantService.generateMerchantInvoice(requestInvoiceDTO);
	}

	@GetMapping("paymentRequests")
	public List<PaymentRequestDTO> getAllPaymentRequests(@RequestParam Integer userId) throws UserException {
		return this.customerService.getAllPaymentRequests(userId);
	}

	@PostMapping("Login")
	public String loginUser(@RequestParam String email,@RequestParam String password)throws UserException{
		return userLoginService.logIn(email,password);
	}

	@PutMapping("Logout/{userId}")
	public String logoutUser(@PathVariable Integer userId)throws UserException{
		return userLoginService.logOut(userId);
	}

}
