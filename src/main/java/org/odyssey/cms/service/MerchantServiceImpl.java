package org.odyssey.cms.service;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.PaymentRequestDTO;
import org.odyssey.cms.dto.RequestInvoiceDTO;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.TransactionException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MerchantServiceImpl implements MerchantService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;

	@Autowired
	private NotificationService notificationService;
	@Autowired
	private TransactionService transactionService;

	@Override
	public User createNewMerchant(UserRegistrationDTO userRegistrationDTO) throws AccountException, UserException, CreditCardException {
		Optional<User> addUser = this.userRepository.findById(userRegistrationDTO.getUserId());
		if (addUser.isPresent()) {
			throw new UserException("User already exist");
		}
		if (userRegistrationDTO.getEmail().equals("admin@gmail.com")){
			throw new UserException("User already exist");
		}
		User user = new User();
		user.setUserId(0);
		user.setName(userRegistrationDTO.getName());
		user.setPhone(userRegistrationDTO.getPhone());
		user.setEmail(userRegistrationDTO.getEmail());
		user.setAddress(userRegistrationDTO.getAddress());
		user.setType("Merchant");
		user.setLogin(false);
		Account account = new Account();
		account.setAccountId(0);
		account.setBalance(10000.0);
		account.setPassword(userRegistrationDTO.getAccountPassword());
		account = this.accountService.createAccount(account, "Merchant");
		user.setAccount(account);
		this.userRepository.save(user);
    	this.notificationService.saveNotification(user.getUserId(),"Merchant","user merchant created");
		return user;
	}

	@Override
	public Boolean newRequest(Integer paymentRequestId, Integer merchantId, Integer customerId,Double amount, String topic) throws AccountException{
		Optional<User> accountOptionalMerchant = this.userRepository.findById(merchantId);
		Optional<User> accountOptionalCustomer = this.userRepository.findById(customerId);
//		if(accountOptionalCustomer.get().getLogin()==false){
//			throw new AccountException("Not Login");
//		}
		if (accountOptionalMerchant.isEmpty()) {
			throw new AccountException("merchant Account doesn't exists: ");
		}
		else if (accountOptionalCustomer.isEmpty()) {
			throw new AccountException("customer Account doesn't exists: ");
		}
		PaymentRequest paymentRequest=new PaymentRequest(0,merchantId,customerId, LocalDateTime.now(), topic, "PENDING",amount);
		notificationService.saveNotification(merchantId,"Merchant","Merchant Requested a payment of "+amount+" to "+accountOptionalCustomer.get().getName());
		notificationService.saveNotification(customerId,"Customer","Customer Received a Payment Request of "+amount+" from "+accountOptionalMerchant.get().getName());
		this.paymentRequestRepository.save(paymentRequest);
		return true;
	}

	@Override
	public List<PaymentRequestDTO> getAllPaymentRequestsForMerchant(Integer userId) throws UserException
	{
		Optional<User> optionalUser = this.userRepository.findById(userId);
		if (optionalUser.isEmpty()) {
			throw new UserException("User doesn't exist");
		}
		User user = optionalUser.get();
		if (user.getType().equals("Merchant")) {
			return this.paymentRequestRepository.findByMerchantId(userId).stream()
					.map(this::convertToDTO).collect(Collectors.toList());
		}

		return new ArrayList<>();
	}

	@Override
	public Invoice generateMerchantInvoice(RequestInvoiceDTO requestInvoiceDTO) throws UserException, PaymentRequestException, TransactionException, AccountException
	{
		Invoice invoice=new Invoice();
		Transaction transaction = transactionService.getTransactionById(requestInvoiceDTO.getTransactionID());
		Integer paymentRequestId = transaction.getPaymentRequestId();
		Optional<PaymentRequest> optionalPaymentRequest = this.paymentRequestRepository.findById(paymentRequestId);
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
//		Integer transactionID = requestInvoiceDTO.getTransactionID();
//		Transaction transaction = transactionService.getTransactionById(transactionID);
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
