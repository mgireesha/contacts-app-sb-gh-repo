package com.capp.springboot.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capp.springboot.object.Contact;
import com.capp.springboot.object.SignInParams;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CApUtil {
	
	private final Logger LOG = LoggerFactory.getLogger(CApUtil.class);
	
    public CApUtil() {
        super();
    }
    
    public static String checkNull(String stringObject){
        if(null==stringObject || "null".equalsIgnoreCase(stringObject)){
            stringObject="";    
        }
    return stringObject;   
    }
    
    public Map<String,String> getColours(String identifier) throws DaoException{
        Map<String,String> colours = new HashMap<String,String>();
        Connection con = null;
        ResultSet colourSet = null;
        Statement stmt =null;
        String navbar = "";
        try{
            String sqlStmt="SELECT * FROM public.cONTACTS_APP_COLOURS WHERE IDENTIFIER='"+identifier+"'";
            con = DbUtil.getConnection();
            stmt = con.createStatement();
            colourSet = stmt.executeQuery(sqlStmt);
            while(colourSet.next()){
                navbar = colourSet.getString("NAVBAR");
                colours.put("navbar", navbar);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            DbUtil.closeResources(con, stmt, colourSet);
        }
    return colours;
    }
    
    
    public Contact resultSetToContact(ResultSet rs) {           
        Contact contact = new Contact();
        try{
            contact.setName(rs.getString("name"));
            contact.setEmail(rs.getString("email"));
            contact.setPhone(rs.getString("phone"));
            contact.setGender(rs.getString("gender"));
            contact.setCity(rs.getString("city")); 
            contact.setAddress(rs.getString("address"));
            contact.setState(rs.getString("state"));
            contact.setId(rs.getString("id"));
            contact.setDp(getImagePath(rs.getBytes("dp"))); // added to for insert and retrieve image to database
        }catch(SQLException e){
            e.printStackTrace();
        }
return contact;
}
    public String getImagePath(byte [] DP) {
    	String imgPath = "";
    	if(null!=DP) {
    		imgPath ="data:image/png;base64," + Base64.getEncoder().encodeToString(DP); 
    	}
    	return imgPath;
    }

	public List<String> getStatesList() {
		Connection con =null;
		Statement stmt = null;
		ResultSet rs =null;
		String getStates = "SELECT NAME FROM STATES";
		List<String> statesList = new ArrayList<String>();
		
		try {
			con = DbUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getStates);
			while(rs.next()) {
				statesList.add(checkNull(rs.getString("NAME")));
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return statesList;
	}

	public List<String> getCityList(String state) {
		Connection con =null;
		Statement stmt = null;
		ResultSet rs =null;
		String getCityies = "SELECT LOWER(CITY_NAME) FROM CITIES WHERE LOWER(CITY_STATE)='"+state.toLowerCase()+"'";
		LOG.info("getCityies: "+getCityies);
		List<String> cityList = new ArrayList<String>();
		
		try {
			con = DbUtil.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(getCityies);
			while(rs.next()) {
				cityList.add(checkNull(rs.getString(1)));
			}
		} catch (DaoException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return cityList;
	}

	public Contact requestObectToContact(HttpServletRequest request) {
		Contact contact = new Contact();
		contact.setName(checkNull(request.getParameter("name")));
		contact.setGender(checkNull(request.getParameter("gender")));
		contact.setEmail(checkNull(request.getParameter("email")));
		contact.setPhone(checkNull(request.getParameter("phone")));
		contact.setAddress(checkNull(request.getParameter("address")));
		contact.setState(checkNull(request.getParameter("state")));
		contact.setCity(checkNull(request.getParameter("city")));
		contact.setId(checkNull(request.getParameter("cid")));
		return contact;
	}
	
	public SignInParams requestObectToSignInParams(HttpServletRequest request) {
		SignInParams signin = new SignInParams();
		signin.setNameFromDb(checkNull(request.getParameter("name")));
		signin.setGender(checkNull(request.getParameter("gender")));
		signin.setEmail(checkNull(request.getParameter("email")));
		signin.setPhone(checkNull(request.getParameter("phone")));
		signin.setPassWordFromDb(checkNull(request.getParameter("newPwd")));
		return signin;
	}
	
	public String getMaXContactid(Connection con, String tablename) {
        String id="100001";
        String searchQuery = "select MAX(id) from "+tablename;
        int tempNum=0;
        ResultSet rs =null;
        Statement stmt = null;
        try {
			stmt = con.createStatement();
			rs=stmt.executeQuery(searchQuery);
			while(rs.next()){
				id=rs.getString(1);
				if(null!=id){
					tempNum=Integer.parseInt(id)+1;
					id=String.valueOf(tempNum);
				}else {
					id="100001";
				}
			}
        } catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
