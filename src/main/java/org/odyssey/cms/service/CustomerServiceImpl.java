package org.odyssey.cms.service;

import lombok.Setter;
import org.odyssey.cms.entity.User;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	User user;
	public void hi(){
		System.out.println(user.getUserId());
	}
}
