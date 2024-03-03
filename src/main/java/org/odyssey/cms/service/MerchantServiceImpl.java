package org.odyssey.cms.service;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;

	@Autowired
	private NotificationService notificationService;

	@Override
	public User createNewMerchant(User newMerchant) throws AccountException, NotificationException {
		if (!newMerchant.getType().equals("merchant")){
			throw new AccountException("this user is not a merchant user");
		}

		Optional<User> accountOptional = this.userRepository.findById(newMerchant.getUserId());

		if (accountOptional.isPresent()) {
			throw new AccountException("user already exists\n" + accountOptional.get().toString());
		}
		notificationService.saveNotification(newMerchant.getUserId(),"Merchant","user merchant created");
		return userRepository.save(newMerchant);
	}

	@Override
	public Boolean newRequest(Integer paymentRequestId, Integer merchantId, Integer customerId,Double amount) throws AccountException,NotificationException{
		Optional<User> accountOptionalMerchant = this.userRepository.findById(merchantId);
		Optional<User> accountOptionalCustomer = this.userRepository.findById(customerId);
		if (accountOptionalMerchant.isEmpty()) {
			throw new AccountException("merchant Account doesn't exists: ");
		}
		else if (accountOptionalCustomer.isEmpty()) {
			throw new AccountException("customer Account doesn't exists: ");
		}
		PaymentRequest paymentRequest=new PaymentRequest(0,merchantId,customerId,amount);
		notificationService.saveNotification(merchantId,"Merchant","merchant Requested a payment of"+amount+" to "+customerId);
		notificationService.saveNotification(customerId,"Customer","customer receved a Requested of"+amount+" form "+merchantId);
		this.paymentRequestRepository.save(paymentRequest);
		return true;
	}

	@Override
	public Invoice generateMerchantInvoice(Transaction transaction, PaymentRequest paymentRequest) throws UserException {
		Invoice invoice=new Invoice();
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
		return invoice;
	}
}
