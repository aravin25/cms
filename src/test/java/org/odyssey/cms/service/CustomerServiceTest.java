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
import org.odyssey.cms.dto.UserUpdateDTO;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
	private User user1;
	private User user2;
	private User userCheck;
	private Account account;
	private CreditCard creditCard;
	private List<User> userList;
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
	private UserUpdateDTO userUpdateDTO;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@BeforeEach
	public void setUp(){
		creditCard=new CreditCard();
		notificationService.saveNotification(1,"qaz","wsx");
		user1 =new User(0,"qaz","no3","abc@gmail.com","1234567890","Merchant",null);
		user2 =new User(0,"wsx","no4","abcdef@gmail.com","1234567800","Customer",null);
		userRegistrationDTO=new UserRegistrationDTO(0,"wsx","no4","abcdef@gmail.com","1234567800","Customer","Required","Abcdefgh1@10");
		account=new Account(0,1000000.0, null,"Abcdefgh1@10",null,null);
		paymentRequest=new PaymentRequest(0,1,2,100.0);
		invoice=new Invoice();
		requestInvoiceDTO=new RequestInvoiceDTO(null,null);
		userList= Arrays.asList(user1,user2);
		userUpdateDTO=new UserUpdateDTO(0,"no8","abcd@gmail.com","1234567890");
	}

	@Test
	public void createUSerTest()throws AccountException, UserException{
		Mockito.when(userRepository.findById(userRegistrationDTO.getUserId())).thenReturn(Optional.empty());
		Mockito.when(accountService.createAccount(account,"Customer")).thenReturn(null);
		try {
			userCheck=customerService.createUser(userRegistrationDTO);
		} catch (UserException e) {
			Assertions.fail("Exception not Expected"+e.getMessage());
		}
		Assertions.assertEquals(user2,userCheck);
	}


	@Test
	public void createInvoiceTest()throws UserException, PaymentRequestException {
		requestInvoiceDTO.setTransaction(new Transaction(1,100.0, LocalDateTime.now(),null));
		requestInvoiceDTO.setPaymentRequest(new PaymentRequest(1,1,2,100.0));
		paymentRequest=new PaymentRequest(1,1,2,100.0);
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user1));
		Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(user2));
		Mockito.when(paymentRequestRepository.findById(requestInvoiceDTO.transaction.getTransactionID())).thenReturn(Optional.of(paymentRequest));
		invoice=customerService.generateCustomerInvoice(requestInvoiceDTO);
		Optional<Invoice> optionalInvoice=Optional.of(invoice);
		Assertions.assertEquals(true,optionalInvoice.isPresent());
	}

	@Test
	public void getAllUsersTest(){
		Mockito.when(userRepository.findAll()).thenReturn(userList);
		List<User> checkList=customerService.getAllUsers();
		Assertions.assertEquals(userList,checkList);
	}

	@Test
	public void updateUser()throws UserException{
		Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user1));
		Mockito.when(userRepository.save(user1)).thenReturn(user1);
		User user3=customerService.updateUser(userUpdateDTO);
		Assertions.assertEquals(user1,user3);
	}

	@Test
	public void getUserByIdTest()throws UserException{
		Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user1));
		User user3=customerService.getUserById(0);
		Assertions.assertEquals(user1,user3);
	}

	@Test
	public void getAllUserTest(){
		Mockito.when(userRepository.findAll()).thenReturn(userList);
		List<User> checkList=customerService.getAllUser();
		Assertions.assertEquals(userList,checkList);
	}

	@Test
	public void deleteUserTest()throws UserException{
		Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user1));
		List<User> checkList=customerService.getAllUser();
		Assertions.assertEquals("successfully deleted",customerService.deleteUser(0));
	}

	@Test
	public void getAllPaymentRequestTest1()throws UserException{
		Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user1));
		Mockito.when(paymentRequestRepository.findByMerchantId(0)).thenReturn(Arrays.asList(paymentRequest));
		List<User> checkList=customerService.getAllUser();
		Assertions.assertEquals(Arrays.asList(paymentRequest),customerService.getAllPaymentRequests(0));
	}

	@Test
	public void getAllPaymentRequestTest2()throws UserException{
		Mockito.when(userRepository.findById(0)).thenReturn(Optional.of(user2));
		Mockito.when(paymentRequestRepository.findByCustomerId(0)).thenReturn(Arrays.asList(paymentRequest));
		List<User> checkList=customerService.getAllUser();
		Assertions.assertEquals(Arrays.asList(paymentRequest),customerService.getAllPaymentRequests(0));
	}
}
