package com.capp.springboot.services;

public interface UpdateService {

	String updatePassword(String currentPwd, String newPwd, String tableName, String isFromForgoPwdMail, String registeredEmail);

	boolean updateColour(String tableName, String bgColour, String navBarBgClr, String hdrTxtClr);

	String updateView(String view, String tableName);

	void updateDp(byte[] fileContent, String id, String tableName);

	int updateContatname(String tableName, String newName, String cId);


}
