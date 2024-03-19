package org.odyssey.cms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.RequestInvoiceDTO;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
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

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MerchantServiceTest {
	private User user1;
	private User user2;
	private User userCheck;
	private Account account;
	private CreditCard creditCard;
	private Transaction transaction;
	@Mock
	private PaymentRequest paymentRequest;

	@Mock
	private Invoice invoice;

	@Mock
	private AccountService accountService;
	@Mock
	private UserRepository userRepository;
	@Mock
	private UserRegistrationDTO userRegistrationDTO;

	@Mock
	private PaymentRequestRepository paymentRequestRepository;

	@Mock
	private RequestInvoiceDTO requestInvoiceDTO;

	@Mock
	private NotificationService notificationService;
	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private MerchantServiceImpl merchantService;

	@BeforeEach
	public void setUp(){
		creditCard=new CreditCard();
		notificationService.saveNotification(1,"qaz","wsx");
		user1 =new User(0,"qaz","no3","abc@gmail.com","1234567890","Merchant",null);
		user2 =new User(0,"wsx","no4","abcdef@gmail.com","1234567800","Customer",null);
		userRegistrationDTO=new UserRegistrationDTO(0,"qaz","no3","abc@gmail.com","1234567890","Merchant","Required","Abcdefgh1@10");
		account=new Account(0,10000.0, null,"Abcdefgh1@10",null,null);
		paymentRequest=new PaymentRequest(0,1,2,100.0);
		invoice=new Invoice();
		requestInvoiceDTO=new RequestInvoiceDTO(null,null);
		transaction = new Transaction(1,100.0,LocalDateTime.now(),null);
	}
	@Test
	public void createTest() throws AccountException, CreditCardException {
		Mockito.when(userRepository.findById(userRegistrationDTO.getUserId())).thenReturn(Optional.empty());
		Mockito.when(accountService.createAccount(account,"Merchant")).thenReturn(null);
		try {
			userCheck=merchantService.createNewMerchant(userRegistrationDTO);
		} catch (UserException e) {
			Assertions.fail("Exception not Expected"+e.getMessage());
		}
		Assertions.assertEquals(user1,userCheck);
	}

	@Test
	public void createTestNegative()throws AccountException{
		Mockito.when(userRepository.findById(userRegistrationDTO.getUserId())).thenReturn(Optional.of(user1));
		Assertions.assertThrows(UserException.class,()->{
			userCheck=merchantService.createNewMerchant(userRegistrationDTO);
		});
	}
	@Test
	public void createRequestTest()throws AccountException{
		Boolean checkBoolean;
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user1));
		Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(user2));
		Mockito.when(paymentRequestRepository.save(paymentRequest)).thenReturn(paymentRequest);
		checkBoolean=merchantService.newRequest(1,1,2,100.0);
		Assertions.assertEquals(true,checkBoolean);
	}

	@Test
	public void createRequestException1()throws AccountException{
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.empty());
		Mockito.when(userRepository.findById(2)).thenReturn(Optional.empty());
		Assertions.assertThrows(AccountException.class,()->{
			merchantService.newRequest(1,1,2,100.0);
		});
	}
	@Test
	public void createRequestException2()throws AccountException{
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user1));
		Mockito.when(userRepository.findById(2)).thenReturn(Optional.empty());
		Assertions.assertThrows(AccountException.class,()->{
			merchantService.newRequest(1,1,2,100.0);
		});
	}

	@Test
	public void createInvoiceTest() throws UserException, PaymentRequestException, TransactionException, AccountException
	{
		requestInvoiceDTO.setTransactionID(transaction.getTransactionID());
		requestInvoiceDTO.setPaymentRequestID(paymentRequest.getPaymentRequestId());
		paymentRequest=new PaymentRequest(1,1,2,100.0);
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user1));
		Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(user2));
		Mockito.when(paymentRequestRepository.findById(0)).thenReturn(Optional.of(paymentRequest));
		Mockito.when(transactionService.getTransactionById(1)).thenReturn(transaction);
		invoice=merchantService.generateMerchantInvoice(requestInvoiceDTO);
		Optional<Invoice> optionalInvoice=Optional.of(invoice);
		Assertions.assertEquals(true, optionalInvoice.isPresent());
	}
}
