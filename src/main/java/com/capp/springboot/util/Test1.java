package com.capp.springboot.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;

import com.capp.springboot.object.SignInParams;

public class Test1 {
public static void main(String[] args) {
	/*
	 * File imagePath = new File(
	 * "D:\\SWorkspace\\ContactsAppSpringBoot\\src\\main\\webapp\\static_resources\\user_images\\CONTACTS_APP_98"
	 * ); try { FileUtils.deleteDirectory(imagePath); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 */
	Test1 test = new Test1();
	//test.addColumn();
	test.getColours();
	test.getUsers();
	//test.createUser();
	/*
	 * test.createCredsTable(); test.createColourTable(); test.createUser();
	 */
	
}
public void getTables(String schema) {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		con = DbUtil.getConnection("cloudPostgress");
		String selQuery = "SELECT * FROM information_schema.tables WHERE table_schema = '"+schema+"'";
		stmt = con.createStatement();
		rs = stmt.executeQuery(selQuery);
		while(rs.next()) {
			System.out.println(rs.getString("TABLE_NAME"));
		}
	} catch (Exception e) {
		try {
			DbUtil.closeResources(con, stmt, rs);
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Failed to create table");
		e.printStackTrace();
	}
}
public void addColumn() {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		con = DbUtil.getConnection("cloudPostgress");
		String altertable = "ALTER TABLE public.CONTACTS_APP_CREDS ADD COLUMN OTP varchar(250)";
		stmt = con.createStatement();
		stmt.executeUpdate(altertable);
		while(rs.next()) {
			System.out.println(rs.getString("USERNAME"));
			System.out.println(rs.getString("PASSWORD"));
			System.out.println(rs.getString("tablename"));
		}
	} catch (Exception e) {
		try {
			DbUtil.closeResources(con, stmt, rs);
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Failed to create table");
		e.printStackTrace();
	}
}

public void getUsers() {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		con = DbUtil.getConnection("cloudPostgress");
		String getUsers = "SELECT * FROM public.CONTACTS_APP_CREDS";// WHERE USERNAME='mgireesha73@gmail.com' AND OTP='48f5f23'";
		stmt = con.createStatement();
		rs = stmt.executeQuery(getUsers);
		while(rs.next()) {
			System.out.println(rs.getString("NAME")+", "+rs.getString("USERNAME")+", "+rs.getString("PASSWORD")+", "+rs.getString("TABLENAME"));
		}
	} catch (Exception e) {
		try {
			DbUtil.closeResources(con, stmt, rs);
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Failed to create table");
		e.printStackTrace();
	}
}

public void getColours() {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	try {
		con = DbUtil.getConnection("cloudPostgress");
		String getUsers = "SELECT * FROM public.CONTACTS_APP_COLOURS";// WHERE USERNAME='mgireesha73@gmail.com' AND OTP='48f5f23'";
		stmt = con.createStatement();
		rs = stmt.executeQuery(getUsers);
		while(rs.next()) {
			System.out.println(rs.getString("IDENTIFIER")+", "+rs.getString("NAVBAR")+", "+rs.getString("HEADERTEXT"));
		}
	} catch (Exception e) {
		try {
			DbUtil.closeResources(con, stmt, rs);
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Failed to create table");
		e.printStackTrace();
	}
}

public void createCredsTable() {
	Connection con = null;
	Statement stmt = null;
	try {
		con = DbUtil.getConnection("cloudPostgress");
		if(null!=con)
			System.out.println("Suucessfully connected to postgres");
		else
			System.out.println("Connection failed");
		String createCredsTable = "CREATE TABLE CONTACTS_APP_CREDS"
				+ "(USERNAME VARCHAR(250),PASSWORD VARCHAR(250),TABLENAME VARCHAR(250)"
				+ ",NAME VARCHAR(250),PHONE VARCHAR(250),GENDER VARCHAR(250),ID VARCHAR(250)"
				+ ",VIEW VARCHAR(250))";
		stmt = con.createStatement();
		stmt.executeUpdate(createCredsTable);
		System.out.println("Table CONTACT_APP_CREDS created successfully");
	} catch (Exception e) {
		System.out.println("Failed to create table");
		e.printStackTrace();
	}
}

public void createColourTable() {
	Connection con = null;
	Statement stmt = null;
	try {
		con = DbUtil.getConnection("cloudPostgress");
		if(null!=con)
			System.out.println("Suucessfully connected to postgres");
		else
			System.out.println("Connection failed");
		String createColursTable = "CREATE TABLE CONTACTS_APP_COLOURS"
				+ "(IDENTIFIER VARCHAR(200),NAVBAR VARCHAR(200),"
				+ "HEADERTEXT VARCHAR(200))";
		stmt = con.createStatement();
		stmt.executeUpdate(createColursTable);
		System.out.println("Table CONTACTS_APP_COLOURS created successfully");
	} catch (Exception e) {
		System.out.println("Failed to create table");
		e.printStackTrace();
	}
}

public String createUser() {
	String status = "";
	int statInt = 0 ;
	Connection con = null;
	PreparedStatement ps = null;
	Statement stmt = null;
	String usertable = null;
	int id = 0;
	CApUtil cUtil = new CApUtil();
	SignInParams userObj = new SignInParams();
	userObj.setEmail("mgireesha73@gmail.com");
	userObj.setPassWordFromDb("a");
	userObj.setNameFromDb("Gireesh MT");
	userObj.setPhone("9876543210");
	userObj.setGender("Male");
	String saveUser = "INSERT INTO public.CONTACTS_APP_CREDS (username, password, tableName,name, phone, gender, id, view) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	String saveToColour = "INSERT INTO public.CONTACTS_APP_COLOURS (IDENTIFIER,NAVBAR,HEADERTEXT) VALUES (?,?,?)";
	try {
		con = DbUtil.getConnection("cloudPostgress");
		String maxId = cUtil.getMaXContactid(con, "public.CONTACTS_APP_CREDS");
		id = Integer.parseInt(maxId);
		usertable = "CONTACTS_APP_"+id;
		ps = con.prepareStatement(saveUser);
		ps.setString(1, userObj.getEmail());
		ps.setString(2, userObj.getPassWordFromDb());
		ps.setString(3, usertable);
		ps.setString(4, userObj.getNameFromDb());
		ps.setString(5, userObj.getPhone());
		ps.setString(6, userObj.getGender());
		ps.setInt(7, id);
		ps.setString(8, "basic");
		//statInt = ps.executeUpdate();
		statInt=1;
		if(statInt==1) {
			ps = con.prepareStatement(saveToColour); 
			ps.setString(1, usertable);
			ps.setString(2, "");
			ps.setString(3, "");
			//statInt = ps.executeUpdate();
			if(statInt == 1) {
				usertable="CONTACTS_APP_100004";
				String createTable="CREATE TABLE "+usertable+" (NAME VARCHAR(200),GENDER VARCHAR(20),EMAIL VARCHAR(200),PHONE VARCHAR(200),CITY VARCHAR(200),ADDRESS VARCHAR(200),STATE VARCHAR(200),ID VARCHAR(200),DP bytea)";
				stmt = con.createStatement();
				stmt.executeUpdate(createTable);
				status = "Success";
			}
		}
	} catch (DaoException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		try {
			DbUtil.closeResources(con, stmt,ps);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	return status;
}
}
