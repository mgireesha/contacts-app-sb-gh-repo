package com.capp.springboot.services;

import org.springframework.stereotype.Service;

@Service
public interface RemoveService {

	String deleteContactById(String cId, String tableName);

	String deleteAccount(String tableName);

	String deleteSelectedContacts(String tableName, String checkedContactIds);

	String verifyPwd(String tableName, String pwd);
	

}
