package org.odyssey.cms.service;

import lombok.Setter;
import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(User newUser) throws AccountException {
		Optional<User> addUser= userRepository.findById(newUser.getUserId());
		if(addUser.isPresent()){
			throw new AccountException("User already exist");
		}
		return userRepository.save(newUser);
	}

	@Override
	public User updateUser(User updateUser) throws AccountException {
		Optional<User> addUser= userRepository.findById(updateUser.getUserId());
		if(!addUser.isPresent()){
			throw new AccountException("User not exist");
		}
		else if(addUser.equals(updateUser)){
			throw new AccountException("no change required");
		}
		return userRepository.save(updateUser);
	}

	@Override
	public User getUserById(Integer userId) throws AccountException {
		Optional<User> getUser = this.userRepository.findById(userId);
		if(!getUser.isPresent()){
			throw new AccountException("Account does not exist!");
		}
		return getUser.get();
	}

	@Override
	public List<User> getAllUser(){
		return this.userRepository.findAll();
	}

	@Override
	public String deleteUser(Integer userId) throws AccountException {
		Optional<User> removeUser = this.userRepository.findById(userId);
		if(!removeUser.isPresent()){
			throw new AccountException("Account does not exist.");
		}
		this.userRepository.deleteById(userId);
		return "successfully deleted";
	}
}
