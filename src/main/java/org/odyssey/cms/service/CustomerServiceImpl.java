package org.odyssey.cms.service;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.RequestInvoiceDTO;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.dto.UserUpdateDTO;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

	@Autowired
	private NotificationService notificationService;

	@Override
	public List<User> getAllUsers() {
	  return this.userRepository.findAll();
  }

	@Override
	public User createUser(UserRegistrationDTO userRegistrationDTO) throws AccountException, UserException  {
		Optional<User> addUser = this.userRepository.findById(userRegistrationDTO.getUserId());
		if (addUser.isPresent()) {
			throw new UserException("User already exist");
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

		account = this.accountService.createAccount(account, "Customer");

		user.setAccount(account);
		this.userRepository.save(user);
		notificationService.saveNotification(user.getUserId(),"Customer","customer created");
		return user;
	}

	@Override
	public User updateUser(UserUpdateDTO userUpdateDTO) throws UserException  {
		Optional<User> addUser = userRepository.findById(userUpdateDTO.getUserId());
		if (addUser.isEmpty()) {
			throw new UserException("User doesn't exist");
		}
		User addUser1 = addUser.get();
		addUser1.setAddress(userUpdateDTO.getAddress());
		addUser1.setEmail(userUpdateDTO.getEmail());
		addUser1.setPhone(userUpdateDTO.getPhone());
    notificationService.saveNotification(addUser.get().getUserId(),"Customer","Detaile Updated");
		return userRepository.save(addUser1);
	}

	@Override
	public User getUserById(Integer userId) throws UserException {
		Optional<User> getUser = this.userRepository.findById(userId);
		if (getUser.isEmpty()) {
			throw new UserException("User does not exist!");
		}
		return getUser.get();
	}

	@Override
	public List<User> getAllUser() {
		return this.userRepository.findAll();
	}

	@Override
	public String deleteUser(Integer userId) throws UserException {
		Optional<User> removeUser = this.userRepository.findById(userId);
		if (removeUser.isEmpty()) {
			throw new UserException("User does not exist.");
		}
		this.userRepository.deleteById(userId);
		return "successfully deleted";
	}

	@Override
	public Invoice generateCustomerInvoice(RequestInvoiceDTO requestInvoiceDTO) throws UserException, PaymentRequestException {
		Invoice invoice=new Invoice();
		Optional<PaymentRequest> optionalPaymentRequest = this.paymentRequestRepository.findById(requestInvoiceDTO.transaction.getTransactionID());
		if (optionalPaymentRequest.isEmpty()){
			throw new PaymentRequestException("Payment does not exist");
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

	@Override
	public List<PaymentRequest> getAllPaymentRequests(Integer userId) throws UserException {
		Optional<User> optionalUser = this.userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			throw new UserException("User doesn't exist");
		}
		User user = optionalUser.get();
		if (user.getType().equals("Customer")) {
			return this.paymentRequestRepository.findByCustomerId(userId);
		} else if (user.getType().equals("Merchant")) {
			return this.paymentRequestRepository.findByMerchantId(userId);
		}

		return new ArrayList<>();
	}
}
