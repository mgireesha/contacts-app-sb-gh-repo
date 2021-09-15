package com.capp.springboot.services;

import java.util.List;

import com.capp.springboot.object.Contact;


public interface SaveContactService {
	
	public void saveContacts(List<Contact> cList, String tableName);

	public void updateContactById(Contact contact, String tableName);

	public String getMaXContactid(String tableName);

}
