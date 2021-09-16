package com.capp.springboot.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.capp.springboot.object.SignInParams;
import com.capp.springboot.util.CApUtil;
import com.capp.springboot.util.DaoException;
import com.capp.springboot.util.DbUtil;


public class LoginServiceImpl implements LoginService{

	public SignInParams validateLogin(String userName, String password) {
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		String errorMsg = "";
		String searchUName = "SELECT * FROM public.contacts_app_creds WHERE USERNAME='"+userName+"'";
		String searchPsswd = "SELECT * FROM public.contacts_app_creds WHERE USERNAME='"+userName+"' AND PASSWORD='"+password+"'";
		SignInParams userDtls = new SignInParams();
		
		try {
			con = DbUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(searchUName);
			if(rs.next()){
				rs = stmt.executeQuery(searchPsswd);
				if(rs.next()) {
					errorMsg = "Success";
					userDtls.setNameFromDb(rs.getString("NAME"));
					userDtls.setUseNameFromDb(rs.getString("USERNAME"));
					userDtls.setTableNameFromDb("public."+rs.getString("TABLENAME"));
					userDtls.setViewFromDb(rs.getString("VIEW"));
					userDtls.setErrMsg(errorMsg);
					userDtls.setIsShwRed(false);
				}else {
					errorMsg = "Entered password is not correct";
					userDtls.setIsShwRed(false);
				}
					
			}else {
				errorMsg = "User doesn't exists";
				userDtls.setIsShwRed(false);
				
			}
			userDtls.setErrMsg(errorMsg);
				
			
		} catch (DaoException e) {
			if(e.getMessage().contains("socket creation error")) {
				userDtls.setErrMsg("Server is down, contact adminstrator");
			}
			
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				DbUtil.closeResources(con, stmt, rs);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		
		return userDtls;
	}

	@Override
	public String registeruser(SignInParams userObj, String userDetailTable) {
		String status = "";
		int statInt = 0 ;
		Connection con = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		String usertable = null;
		int id = 0;
		CApUtil cUtil = new CApUtil();
		String saveUser = "INSERT INTO public."+userDetailTable+" (username, password, tableName,name, phone, gender, id, view) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String saveToColour = "INSERT INTO public.CONTACTS_APP_COLOURS (IDENTIFIER,NAVBAR,HEADERTEXT) VALUES (?,?,?)";
		try {
			con = DbUtil.getConnection();
			String maxId = cUtil.getMaXContactid(con, "public."+userDetailTable);
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
			statInt = ps.executeUpdate();
			if(statInt==1) {
				ps = con.prepareStatement(saveToColour); 
				ps.setString(1, usertable);
				ps.setString(2, "");
				ps.setString(3, "");
				statInt = ps.executeUpdate();
				if(statInt == 1) {
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
