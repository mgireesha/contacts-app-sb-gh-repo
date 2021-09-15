package com.capp.springboot.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import com.capp.springboot.object.Contact;
import com.capp.springboot.util.CApUtil;
import com.capp.springboot.util.DaoException;
import com.capp.springboot.util.DbUtil;


public class SaveContactsServiceImpl implements SaveContactService {

	public void saveContacts(List<Contact> cList, String tablename) {
		Connection con =null;
		Iterator<Contact> cIt = cList.iterator();
		CApUtil cUtil = new CApUtil();
		Contact contact =null;
		try {
			con = DbUtil.getConnection();
			String maxCntId = cUtil.getMaXContactid(con,tablename);
			while(cIt.hasNext()) {
				maxCntId= String.valueOf(Integer.parseInt(maxCntId)+1);
				contact = cIt.next();
				saveContact(contact, con, tablename, maxCntId);
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtil.closeResources(con);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
	}

	
	public String getMaXContactid(String tablename) {
		Connection con = null;
        String id=null;
        ResultSet rs =null;
        Statement stmt = null;
        String searchQuery = "select MAX(id) from "+tablename;
        try {
        	con=DbUtil.getConnection();
			stmt = con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			while(rs.next()){
				id=rs.getString(1);
			}
        } catch (DaoException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	private void saveContact(Contact contact, Connection con, String tablename, String maxCntId) {
		PreparedStatement pStmt = null;
		String sql = "INSERT INTO "+tablename+" (NAME, GENDER, EMAIL, PHONE, CITY, ADDRESS, STATE, ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pStmt = con.prepareStatement(sql);
			//pStmt.setString(1, CApUtil.checkNull(tablename));
			pStmt.setString(1, CApUtil.checkNull(contact.getName()));
			pStmt.setString(2, CApUtil.checkNull(contact.getGender()));
			pStmt.setString(3, CApUtil.checkNull(contact.getEmail()));
			pStmt.setString(4, CApUtil.checkNull(contact.getPhone()));
			pStmt.setString(5, CApUtil.checkNull(contact.getCity()));
			pStmt.setString(6, CApUtil.checkNull(contact.getAddress()));
			pStmt.setString(7, CApUtil.checkNull(contact.getState()));
			pStmt.setString(8, CApUtil.checkNull(maxCntId));
			pStmt.executeUpdate();
		} catch (SQLException e) {
		}
	}

	@Override
	public void updateContactById(Contact contact, String tableName) {
	    Connection con=null;
	    PreparedStatement ps=null;
	    String updateQuery="UPDATE "+tableName+" SET NAME=?,GENDER=?,EMAIL=?,PHONE=?,CITY=?,ADDRESS=?,STATE=? WHERE ID=?";	
                try{             
                    con=DbUtil.getConnection();
                    System.out.println("connection successfull"); 
                    ps=con.prepareStatement(updateQuery);
                    ps.setString(1, contact.getName());
                    ps.setString(2, contact.getGender());
                    ps.setString(3, contact.getEmail());
                    ps.setString(4, contact.getPhone());
                    ps.setString(5, contact.getCity());
                    ps.setString(6, contact.getAddress());
                    ps.setString(7, contact.getState());
                    ps.setString(8, contact.getId());
                    ps.executeUpdate();
		}catch (DaoException e){
                    e.printStackTrace();
		}catch (SQLException e){
                    e.printStackTrace();
                }finally{
                        try{
                            DbUtil.closeResources(con, ps);
                        }catch (DaoException e){
                            e.printStackTrace();
                        }
                }
		
	}

}
