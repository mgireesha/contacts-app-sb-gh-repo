package com.capp.springboot.services;

import java.util.List;

import com.capp.springboot.object.Contact;

public interface ListContactService {

	List<com.capp.springboot.object.Contact> getAllContacts(String tableName);

	Contact getContactById(String cId, String tableName);

}
