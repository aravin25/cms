package org.odyssey.cms.service;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.PaymentRequestDTO;
import org.odyssey.cms.dto.RequestInvoiceDTO;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.dto.UserUpdateDTO;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.CreditCardException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.TransactionException;
import org.odyssey.cms.exception.UserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
	List<User> getAllUsers();

	public User createUser(UserRegistrationDTO userRegistrationDTO) throws AccountException, UserException, CreditCardException;
	public User updateUser(UserUpdateDTO userUpdateDTO) throws AccountException, UserException;
	public User getUserById(Integer userId) throws AccountException, UserException;
	public List<User> getAllUser();
	public String deleteUser(Integer userId) throws AccountException, UserException;
	public Invoice generateCustomerInvoice(RequestInvoiceDTO requestInvoiceDTO) throws UserException, PaymentRequestException, TransactionException, AccountException;
	public List<PaymentRequestDTO> getAllPaymentRequests(Integer userId) throws UserException;
}
