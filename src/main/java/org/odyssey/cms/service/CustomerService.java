package org.odyssey.cms.service;

import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
	public User createUser(UserRegistrationDTO userRegistrationDTO)throws AccountException;
	public User updateUser(User updateUser)throws AccountException;
	public User getUserById(Integer userId)throws AccountException;
  List<User> getAllUsers();
	public List<User> getAllUser();
	public String deleteUser(Integer userId)throws AccountException;
	public String paymentNotification(Integer customerId)throws AccountException;

}
