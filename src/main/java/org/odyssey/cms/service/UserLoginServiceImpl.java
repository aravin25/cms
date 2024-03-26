package org.odyssey.cms.service;

import org.odyssey.cms.entity.Account;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.UserException;
import org.odyssey.cms.repository.AccountRepository;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginServiceImpl implements UserLoginService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Override
	public User logIn(String email, String password) throws UserException {
		Optional<User> checkUser= userRepository.findByEmail(email);
		if (checkUser.isEmpty()){
			throw new UserException("Enter Valid User Name");
		}
		User user=checkUser.get();
		Account account= user.getAccount();
		String passwordCheck=account.getPassword();
		if (!passwordCheck.equals(password)){
			throw new UserException("Invalid Password");
		}
		user.setLogin(true);
		userRepository.save(user);

		return user;
	}

	@Override
	public String logOut(Integer userId) throws UserException {
		Optional<User> userLogout=userRepository.findById(userId);
		if (userLogout.isEmpty()){
			throw new UserException("User Id Not Valid");
		}
		User user=userLogout.get();
		if (user.getLogin()==false){
			return "Already Logout";
		}
		else {
			user.setLogin(false);
			userRepository.save(user);
			return "Logout successful";
		}
	}
}
