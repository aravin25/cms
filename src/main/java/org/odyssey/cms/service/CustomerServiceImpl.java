package org.odyssey.cms.service;

import org.odyssey.cms.entity.User;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}
}
