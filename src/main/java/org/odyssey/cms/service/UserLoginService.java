package org.odyssey.cms.service;

import org.odyssey.cms.exception.UserException;

public interface UserLoginService {
	public String logIn(String email,String password)throws UserException;
	public String logOut(Integer userId)throws UserException;
}
