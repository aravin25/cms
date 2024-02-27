package org.odyssey.cms.service;

import lombok.Setter;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.exception.AccountException;
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
		user.setUserId(userRegistrationDTO.getUserId());
		user.setName(userRegistrationDTO.getName());
		user.setPhone(userRegistrationDTO.getPhone());
		user.setEmail(userRegistrationDTO.getEmail());
		user.setAddress(userRegistrationDTO.getAddress());
		user.setType(userRegistrationDTO.getType());
		user.setStatus(userRegistrationDTO.getStatus());

		Account account = new Account();
		account.setAccountId(0);
		account.setBalance(0.0);
		account.setPassword(userRegistrationDTO.getAccountPassword());

		account = this.accountService.createAccount(account);

		user.setAccount(account);
		this.userRepository.save(user);

		return user;
	}

	@Override
	public User updateUser(User updateUser) throws AccountException {
		Optional<User> addUser = userRepository.findById(updateUser.getUserId());
		if (!addUser.isPresent()) {
			throw new AccountException("User not exist");
		} else if (addUser.equals(updateUser)) {
			throw new AccountException("no change required");
		}
		return userRepository.save(updateUser);
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
}
