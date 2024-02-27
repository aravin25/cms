package org.odyssey.cms.controller;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;
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
	@Autowired
	private Invoice invoice;

	@PostMapping("/merchant")
	public User createMerchant(@RequestBody User user) throws AccountException{
		
		return this.merchantService.createNewMerchant(user);
	}

	@PostMapping("/merchant/paymentrequest")
	public Boolean newPaymentRequest(@RequestBody PaymentRequest paymentRequest)throws AccountException{
		return this.merchantService.newRequest(0,paymentRequest.getMerchantId(),paymentRequest.getCustomerId(),paymentRequest.getRequestAmount());
	}

	@GetMapping("/customer/{customerId}")
	public String customerPaymentRequest(@PathVariable Integer customerId)throws AccountException{
		return this.customerService.paymentNotification(customerId);
	}

	@PostMapping("/create")
	public User createnewUser(@RequestBody UserRegistrationDTO userRegistrationDTO) throws AccountException {
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
	public User updateAccount(@RequestBody User user)throws AccountException{
		return this.customerService.updateUser(user);
	}

	@DeleteMapping("delete/{userId}")
	public String deleteAccountById(@PathVariable("userId") Integer userId)throws AccountException{
		return this.customerService.deleteUser(userId);
	}

	@GetMapping("cms/customer/requestInvoice")
	public Invoice generateCustomerInvoice(Transaction transaction, PaymentRequest paymentRequest) throws UserException {
		Optional<User> optionalCustomer = this.userRepository.findById(paymentRequest.getCustomerId());
		Optional<User> optionalMerchant = this.userRepository.findById(paymentRequest.getMerchantId());
		if(optionalCustomer.isEmpty()){
			throw new UserException("Customer does not exist");
		} else if (optionalMerchant.isEmpty()) {
			throw new UserException("Merchant does not exist");
		}
		User customer = optionalCustomer.get();
		User merchant = optionalMerchant.get();
		StringBuilder invoiceBody = new StringBuilder();
		invoiceBody.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		invoiceBody.append("<Invoice>\n");
		invoiceBody.append("	<Id>" + invoice.getInvoiceId() + "</Id>");
		invoiceBody.append("  <Customer>\n");
		invoiceBody.append("    <Name>" + customer.getName() + "</Name>\n");
		invoiceBody.append("    <Address>" + customer.getAddress() + "</Address>\n");
		invoiceBody.append("  </Customer>\n");
		invoiceBody.append("  <Transaction>\n");
		invoiceBody.append("    <Amount>" + transaction.getAmount() + "</Amount>\n");
		invoiceBody.append("    <Date>" + transaction.getTransactionDateTime() + "</Date>\n");
		invoiceBody.append("    <Merchant>" + merchant.getName() + "</Merchant>\n");
		invoiceBody.append("  </Transaction>\n");
		invoiceBody.append("</Invoice>\n");
		invoice.setInvoiceBody(invoiceBody.toString());
		return this.invoice;
	}

	@GetMapping("cms/merchant/requestInvoice")
	public Invoice generateMerchantInvoice(Transaction transaction, PaymentRequest paymentRequest) throws UserException{
		Optional<User> optionalMerchant = this.userRepository.findById(paymentRequest.getMerchantId());
		Optional<User> optionalCustomer = this.userRepository.findById(paymentRequest.getCustomerId());
		if(optionalMerchant.isEmpty()){
			throw new UserException("Merchant does not exist");
		} else if (optionalCustomer.isEmpty()) {
			throw new UserException("Customer does not exist");
		}
		User merchant = optionalMerchant.get();
		User customer = optionalCustomer.get();
		StringBuilder invoiceBody = new StringBuilder();
		invoiceBody.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		invoiceBody.append("<Invoice>\n");
		invoiceBody.append("	<Id>" + invoice.getInvoiceId() + "</Id>");
		invoiceBody.append("  <Merchant>\n");
		invoiceBody.append("    <Name>" + merchant.getName() + "</Name>\n");
		invoiceBody.append("    <Address>" + merchant.getAddress() + "</Address>\n");
		invoiceBody.append("  </Merchant>\n");
		invoiceBody.append("  <Transaction>\n");
		invoiceBody.append("    <Amount>" + transaction.getAmount() + "</Amount>\n");
		invoiceBody.append("    <Date>" + transaction.getTransactionDateTime() + "</Date>\n");
		invoiceBody.append("    <Customer>" + customer.getName() + "</Customer>\n");
		invoiceBody.append("  </Transaction>\n");
		invoiceBody.append("</Invoice>\n");
		invoice.setInvoiceBody(invoiceBody.toString());
		return this.invoice;
	}
}
