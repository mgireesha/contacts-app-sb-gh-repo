package com.capp.springboot.services;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;

import com.capp.springboot.util.DaoException;
import com.capp.springboot.util.DbUtil;


public class RemoveServiceImpl implements RemoveService{

	@Override
	public String deleteContactById(String cId, String tableName) {
		Connection con = null;
		PreparedStatement ps =null;
		Statement stmt =null;
		ResultSet rs = null;
		String status = "failed";
		String deletQuery = "DELETE FROM "+tableName+" WHERE ID='"+cId+"'";
		
		try {
			con = DbUtil.getConnection();
			stmt = con.createStatement();
			int status1 = stmt.executeUpdate(deletQuery);
			if(status1==1) {
				File file1 = new File("D:\\SWorkspace\\ContactsAppSpringBoot\\src\\main\\webapp\\static_resources\\user_images\\"+tableName+"\\"+cId+".jpg");
				boolean status2 = file1.delete();
				if(status2) {
					status = "Success";
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public String deleteAccount(String tableName) {
        Connection conn=null;
        Statement stmt=null;
        int deletSt = 0;
        String status ="";
        String deleteCredDetails="DELETE FROM CONTACTS_APP_CREDS WHERE TABLENAME='"+tableName+"'";
        String deleteColour = "DELETE FROM CONTACTS_APP_COLOURS WHERE IDENTIFIER='"+tableName+"'";
        String dropAccount="DROP TABLE "+tableName;
            try {
                Connection con = DbUtil.getConnection();
                stmt=con.createStatement();
                deletSt = stmt.executeUpdate(deleteCredDetails);
                if(deletSt==1) {
                	deletSt = stmt.executeUpdate(dropAccount);
                	if(deletSt == 1 || deletSt==0) {
                		deletSt = stmt.executeUpdate(deleteColour);
                		if(deletSt == 1)
                			status = "Success";
                	}
                }
                	
                File imagePath = new File("D:\\SWorkspace\\ContactsAppSpringBoot\\src\\main\\webapp\\static_resources\\user_images\\"+tableName);
                FileUtils.deleteDirectory(imagePath);
                
            } catch (SQLException e) {
                    e.printStackTrace();
            } catch (DaoException e) {
                    e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                    try {
            DbUtil.closeResources(conn, stmt);
                    }catch(DaoException e){
                        e.printStackTrace();
                    }
            }
	return status;
	}

	@Override
	public String deleteSelectedContacts(String tableName, String checkedContactIds) {
        Connection conn=null;
        PreparedStatement ps=null;
        String status = "";
        int stInt = 0;
            try {
                conn = DbUtil.getConnection();                   
                String selectedContacts [] = checkedContactIds.split(",");
                String sql="";
                StringBuilder sb = new StringBuilder();
                sb.append("DELETE FROM ");
                sb.append(tableName);
                sb.append(" WHERE ID=");
                int id = 0;
                    for(int i=0;i<selectedContacts.length;i++){
                        id = Integer.parseInt(selectedContacts [i]);
                        sb.append(id);
                        if(i<selectedContacts.length-1)
                        sb.append(" OR ID=");
                }
                        sql=sb.toString();                        
                        ps=conn.prepareStatement(sql);
                        stInt =  ps.executeUpdate();
                        if(stInt == 1)
                        	status = "Success";
            }catch (SQLException e){
                    e.printStackTrace();
            }catch (DaoException e){
                    e.printStackTrace();
            }finally{
                    try {
                        DbUtil.closeResources(conn, ps);
                    }catch(DaoException e){
                        e.printStackTrace();
                    }
            }
			return status;
	}

}
