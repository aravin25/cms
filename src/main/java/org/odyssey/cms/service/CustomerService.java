package org.odyssey.cms.service;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.exception.UserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
	public User createUser(UserRegistrationDTO userRegistrationDTO)throws AccountException, NotificationException;
	public User updateUser(User updateUser)throws AccountException,NotificationException;
	public User getUserById(Integer userId)throws AccountException;
  List<User> getAllUsers();
	public List<User> getAllUser();
	public String deleteUser(Integer userId)throws AccountException;
	public Invoice generateCustomerInvoice(Transaction transaction, PaymentRequest paymentRequest) throws UserException;

}
