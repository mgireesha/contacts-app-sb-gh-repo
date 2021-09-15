package com.capp.springboot.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.capp.springboot.object.Contact;
import com.capp.springboot.util.CApUtil;
import com.capp.springboot.util.DaoException;
import com.capp.springboot.util.DbUtil;

public class ListContactServiceImpl implements ListContactService {

	public List<Contact> getAllContacts(String tableName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs =null;
		String getContacts = "SELECT * FROM "+tableName;
		Contact contact = null;
		CApUtil cUtil = new CApUtil();
		List<Contact> cList = new ArrayList<Contact>();
		
		try {
			con = DbUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getContacts);
			while(rs.next()) {
				contact = cUtil.resultSetToContact(rs);
				cList.add(contact);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtil.closeResources(con, stmt, rs);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		
		return cList;
	}

	public Contact getContactById(String cId, String tableName) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs =null;
		String getContact = "SELECT * FROM "+tableName+" WHERE ID='"+cId+"'";
		Contact contact = null;
		CApUtil cUtil = new CApUtil();
		try {
			con = DbUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getContact);
			while(rs.next()) {
				contact = cUtil.resultSetToContact(rs);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtil.closeResources(con, stmt, rs);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		
		return contact;
	}

}
