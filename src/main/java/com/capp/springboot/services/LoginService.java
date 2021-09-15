package com.capp.springboot.services;

import com.capp.springboot.object.SignInParams;

public interface LoginService {
	SignInParams validateLogin(String userName, String password);

	String registeruser(SignInParams userObj, String userDetailTable);
}
