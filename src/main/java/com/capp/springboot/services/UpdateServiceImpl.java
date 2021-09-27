package com.capp.springboot.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.capp.springboot.util.DaoException;
import com.capp.springboot.util.DbUtil;


public class UpdateServiceImpl implements UpdateService {

	@Override
	public String updatePassword(String currentPwd, String newPwd, String tableName, String isFromForgoPwdMail, String registeredEmail) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		int updateSt = 0;
		String status = "WRONG_PASSWORD";
		String checkCuPwd = "";
		tableName = tableName.substring("public.".length(), tableName.length());
		if("yes".equalsIgnoreCase(isFromForgoPwdMail))
			checkCuPwd = "SELECT * FROM public.CONTACTS_APP_CREDS WHERE USERNAME='"+registeredEmail+"' AND OTP='"+currentPwd+"';";
		else
			checkCuPwd = "SELECT * FROM public.CONTACTS_APP_CREDS WHERE TABLENAME='"+tableName+"' AND PASSWORD='"+currentPwd+"';";
		try {
			con = DbUtil.getConnection();
			stmt = con.createStatement();
			//System.out.println(checkCuPwd);
			rs = stmt.executeQuery(checkCuPwd);
			while(rs.next()) {
				if("yes".equalsIgnoreCase(isFromForgoPwdMail))
					tableName = rs.getString("TABLENAME");
				String updatePswd = "UPDATE public.CONTACTS_APP_CREDS SET PASSWORD='"+newPwd+"' WHERE TABLENAME='"+tableName+"'";
				updateSt = stmt.executeUpdate(updatePswd);
				if(updateSt==1) {
					status="Success";
					break;
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtil.closeResources(con, stmt, rs);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	@Override
	public boolean updateColour(String tableName, String bgColour, String navBarBgClr, String hdrTxtClr) {
		boolean isCUpdated=false;
        Connection con=null;
        Statement stmt=null;
        String updateQuery="";
        if("true".equalsIgnoreCase(navBarBgClr) && "true".equalsIgnoreCase(hdrTxtClr)){
            updateQuery="UPDATE public.CONTACTS_APP_COLOURS SET NAVBAR='"+bgColour+"',HEADERTEXT='"+bgColour+"' WHERE IDENTIFIER='"+tableName.substring("public.".length(), tableName.length())+"'";    
        }else if("true".equalsIgnoreCase(navBarBgClr) && !"true".equalsIgnoreCase(hdrTxtClr)){
            updateQuery="UPDATE public.CONTACTS_APP_COLOURS SET NAVBAR='"+bgColour+"' WHERE IDENTIFIER='"+tableName.substring("public.".length(), tableName.length())+"'";
        }else if(!"true".equalsIgnoreCase(navBarBgClr) && "true".equalsIgnoreCase(hdrTxtClr)){
            updateQuery="UPDATE public.CONTACTS_APP_COLOURS SET HEADERTEXT='"+bgColour+"' WHERE IDENTIFIER='"+tableName.substring("public.".length(), tableName.length())+"'";
        }
        try{             
            con=DbUtil.getConnection();
            stmt=con.createStatement();
            stmt.executeUpdate(updateQuery);
            isCUpdated=true;
        }catch (DaoException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
                try{
                    DbUtil.closeResources(con, stmt);
                }catch (DaoException e){
                    e.printStackTrace();
                }
        }
    return isCUpdated; 
	}

	@Override
	public String updateView(String view, String tableName) {
        Connection con=null;
        Statement stmt=null;
        String updateViewQuery="UPDATE public.CONTACTS_APP_CREDS SET VIEW='"+view+"' WHERE TABLENAME='"+tableName.substring("public.".length(), tableName.length())+"'";
        System.out.println(updateViewQuery);
        try {
                con = DbUtil.getConnection();
                stmt=con.createStatement();
                int updateSt = stmt.executeUpdate(updateViewQuery);
                if(updateSt==1)
                	return "Success";
        }catch (SQLException e){
                e.printStackTrace();
        }catch (DaoException e) {
                e.printStackTrace();
        }finally{
            try {
                DbUtil.closeResources(con, stmt);
            }catch(DaoException e){
                e.printStackTrace();
            }
        }
	return "Error";
	}

	@Override
	public void updateDp(byte[] fileContent, String id, String tableName) {
        PreparedStatement ps2 = null;
        Connection con = null;
        try{
            con = DbUtil.getConnection();
            String sql = "update "+tableName+" set dp=? where id="+id;
                ps2 =con.prepareStatement(sql);
                ps2.setBytes(1, fileContent);
                ps2.executeUpdate();
        }catch (DaoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                DbUtil.closeResources(con, ps2);
            }catch(DaoException e){
                e.printStackTrace();
            }
        }
	}

	@Override
	public int updateContatname(String tableName, String newName, String cId) {
		PreparedStatement ps = null;
		Connection con = null;
		int intStat = 0;
		String updatename = "UPDATE "+tableName+" SET NAME = ? WHERE ID = ?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(updatename);
			ps.setString(1, newName);
			ps.setString(2, cId);
			intStat = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return intStat;
	}

}
