package org.odyssey.cms.service;

import lombok.Setter;
import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.RequestInvoiceDTO;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.dto.UserUpdateDTO;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;
	@Autowired
	private AccountService accountService;

	@Override
	public List<User> getAllUsers() {
	  return this.userRepository.findAll();
  }

	@Override
	public User createUser(UserRegistrationDTO userRegistrationDTO) throws AccountException {
		Optional<User> addUser = this.userRepository.findById(userRegistrationDTO.getUserId());
		if (addUser.isPresent()) {
			throw new AccountException("User already exist");
		}

		User user = new User();
		user.setUserId(0);
		user.setName(userRegistrationDTO.getName());
		user.setPhone(userRegistrationDTO.getPhone());
		user.setEmail(userRegistrationDTO.getEmail());
		user.setAddress(userRegistrationDTO.getAddress());
		user.setType("Customer");

		Account account = new Account();
		account.setAccountId(0);
		account.setBalance(1000000.0);
		account.setPassword(userRegistrationDTO.getAccountPassword());

		account = this.accountService.createAccount(account);

		user.setAccount(account);
		this.userRepository.save(user);

		return user;
	}

	@Override
	public User updateUser(UserUpdateDTO userUpdateDTO) throws AccountException {
		Optional<User> addUser = userRepository.findById(userUpdateDTO.getUserId());
		if (!addUser.isPresent()) {
			throw new AccountException("User not exist");
		}
		User addUser1 = addUser.get();
		addUser1.setAddress(userUpdateDTO.getAddress());
		addUser1.setEmail(userUpdateDTO.getEmail());
		addUser1.setPhone(userUpdateDTO.getPhone());
		return userRepository.save(addUser1);
	}

	@Override
	public User getUserById(Integer userId) throws AccountException {
		Optional<User> getUser = this.userRepository.findById(userId);
		if (!getUser.isPresent()) {
			throw new AccountException("Account does not exist!");
		}
		return getUser.get();
	}

	@Override
	public List<User> getAllUser() {
		return this.userRepository.findAll();
	}

	@Override
	public String deleteUser(Integer userId) throws AccountException {
		Optional<User> removeUser = this.userRepository.findById(userId);
		if (!removeUser.isPresent()) {
			throw new AccountException("Account does not exist.");
		}
		this.userRepository.deleteById(userId);
		return "successfully deleted";
	}

	@Override
	public String paymentNotification(Integer customerId) throws AccountException {
		Optional<PaymentRequest> customerPaymentRequest = this.paymentRequestRepository.findByCustomerId(customerId);
		PaymentRequest customerRequest = customerPaymentRequest.get();
		if (customerPaymentRequest.isEmpty()) {
			return "customer don't have any PaymentRequest";
		} else {
			return ("payment Request Id: " + customerRequest.getPaymentRequestId() + "\ncustomer have request form" + customerRequest.getMerchantId() + "\namount: " + customerRequest.getRequestAmount());
		}
	}

	@Override
	public Invoice generateCustomerInvoice(RequestInvoiceDTO requestInvoiceDTO) throws UserException {
		Invoice invoice=new Invoice();
		Optional<PaymentRequest> optionalPaymentRequest = this.paymentRequestRepository.findById(requestInvoiceDTO.transaction.getTransactionID());
		if (optionalPaymentRequest.isEmpty()){
			throw new UserException("Payment does not exist");
		}
		Optional<User> optionalCustomer = this.userRepository.findById(requestInvoiceDTO.paymentRequest.getCustomerId());
		Optional<User> optionalMerchant = this.userRepository.findById(requestInvoiceDTO.paymentRequest.getMerchantId());
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
		invoiceBody.append("    <Amount>" + requestInvoiceDTO.transaction.getAmount() + "</Amount>\n");
		invoiceBody.append("    <Date>" + requestInvoiceDTO.transaction.getTransactionDateTime() + "</Date>\n");
		invoiceBody.append("    <Merchant>" + merchant.getName() + "</Merchant>\n");
		invoiceBody.append("  </Transaction>\n");
		invoiceBody.append("</Invoice>\n");
		invoice.setInvoiceBody(invoiceBody.toString());
		return invoice;
	}
}
