package org.odyssey.cms.service;

import jakarta.servlet.http.HttpServletResponse;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.UserException;

public interface UserLoginService {
	User logIn(String email, String password) throws UserException;

	public String logOut(Integer userId)throws UserException;
}
