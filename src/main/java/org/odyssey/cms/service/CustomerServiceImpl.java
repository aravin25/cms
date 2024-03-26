package org.odyssey.cms.service;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.PaymentRequestDTO;
import org.odyssey.cms.dto.RequestInvoiceDTO;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.dto.UserUpdateDTO;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.TransactionException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.AccountRepository;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.TransactionRepository;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountService accountService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private TransactionService transactionService;

	@Override
	public List<User> getAllUsers() {
	  return this.userRepository.findAll();
  }

	@Override
	public User createUser(UserRegistrationDTO userRegistrationDTO) throws AccountException, UserException, CreditCardException {
		Optional<User> addUser = this.userRepository.findById(userRegistrationDTO.getUserId());
		if (addUser.isPresent()) {
			throw new UserException("User already exist");
		}
		addUser = this.userRepository.findByEmail(userRegistrationDTO.getEmail());
		if (addUser.isPresent()) {
			throw new UserException("User by this Email already exist");
		}

		User user = new User();
		user.setUserId(0);
		user.setName(userRegistrationDTO.getName());
		user.setPhone(userRegistrationDTO.getPhone());
		user.setEmail(userRegistrationDTO.getEmail());
		user.setAddress(userRegistrationDTO.getAddress());
		user.setType("Customer");
		user.setLogin(false);

		Account account = new Account();
		account.setAccountId(0);
		account.setBalance(1000000.0);
		account.setPassword(userRegistrationDTO.getAccountPassword());
		account.setBankType(userRegistrationDTO.getBankType());

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
		if(addUser1.getLogin()==false){
			throw new UserException("Not Login");
		}
		addUser1.setAddress(userUpdateDTO.getAddress());
		addUser1.setEmail(userUpdateDTO.getEmail());
		addUser1.setPhone(userUpdateDTO.getPhone());
    	notificationService.saveNotification(addUser.get().getUserId(),"Customer","Detail Updated");
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
		if (removeUser.get().getLogin()==false){
			throw new UserException("Not Login");
		}
		this.userRepository.deleteById(userId);
		return "successfully deleted";
	}

	@Override
	public Invoice generateCustomerInvoice(RequestInvoiceDTO requestInvoiceDTO) throws UserException, PaymentRequestException, TransactionException, AccountException
	{
		Invoice invoice=new Invoice();
		Optional<PaymentRequest> optionalPaymentRequest = this.paymentRequestRepository.findById(requestInvoiceDTO.getPaymentRequestID());
		if (optionalPaymentRequest.isEmpty()){
			throw new PaymentRequestException("Payment does not exist");
		}
		PaymentRequest paymentRequest = optionalPaymentRequest.get();
		Optional<User> optionalCustomer = this.userRepository.findById(paymentRequest.getCustomerId());
		Optional<User> optionalMerchant = this.userRepository.findById(paymentRequest.getMerchantId());
		if(optionalCustomer.isEmpty()){
			throw new UserException("Customer does not exist");
		}
		if (optionalMerchant.isEmpty()) {
			throw new UserException("Merchant does not exist");
		}
		Integer transactionID = requestInvoiceDTO.getTransactionID() ;
		Transaction transaction = transactionService.getTransactionById(transactionID);
		User customer = optionalCustomer.get();
		User merchant = optionalMerchant.get();
		StringBuilder invoiceBody = new StringBuilder();
		invoiceBody.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		invoiceBody.append("<invoice>\n");
		invoiceBody.append("  <customer>\n");
		invoiceBody.append("    <name>" + customer.getName() + "</name>\n");
		invoiceBody.append("    <address>" + customer.getAddress() + "</address>\n");
		invoiceBody.append("  </customer>\n");
		invoiceBody.append("  <transaction>\n");
		invoiceBody.append("    <amount>" + paymentRequest.getRequestAmount() + "</amount>\n");
		invoiceBody.append("    <date>" + transaction.getTransactionDateTime() + "</date>\n");
		invoiceBody.append("    <merchant>" + merchant.getName() + "</merchant>\n");
		invoiceBody.append("  </transaction>\n");
		invoiceBody.append("</invoice>\n");
		invoice.setInvoiceBody(invoiceBody.toString());
		return invoice;
	}

	@Override
	public List<PaymentRequestDTO> getAllPaymentRequests(Integer userId) throws UserException {
		Optional<User> optionalUser = this.userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			throw new UserException("User doesn't exist");
		}
		User user = optionalUser.get();
		if (user.getType().equals("Customer")) {
			return this.paymentRequestRepository.findByCustomerId(userId).stream()
					.map(this::convertToDTO).collect(Collectors.toList());
		}

//		} else if (user.getType().equals("Merchant")) {
//			return this.paymentRequestRepository.findByMerchantId(userId);
//		}

		return new ArrayList<>();
	}

	public PaymentRequestDTO convertToDTO(PaymentRequest paymentRequest) {
		PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
		User user = this.userRepository.findById(paymentRequest.getMerchantId()).get();
		paymentRequestDTO.setPaymentRequestId(paymentRequest.getPaymentRequestId());
		paymentRequestDTO.setRequestAmount(paymentRequest.getRequestAmount());
		paymentRequestDTO.setPaymentRequestDate(paymentRequest.getPaymentRequestDate());
		paymentRequestDTO.setTopic(paymentRequest.getTopic());
		paymentRequestDTO.setMerchantName(user.getName());
		paymentRequestDTO.setStatus(paymentRequest.getStatus());
		return paymentRequestDTO;
	}
}
